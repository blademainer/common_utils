/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiongyingqi.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AntPathMatcher implements PathMatcher {

    private static final int CACHE_TURNOFF_THRESHOLD = 65536;

    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{[^/]+?\\}");

    
    public static final String DEFAULT_PATH_SEPARATOR = "/";


    private String pathSeparator = DEFAULT_PATH_SEPARATOR;

    private boolean trimTokens = true;

    private volatile Boolean cachePatterns;

    final Map<String, AntPathStringMatcher> stringMatcherCache =
            new ConcurrentHashMap<String, AntPathStringMatcher>(256);


    
    public void setPathSeparator(String pathSeparator) {
        this.pathSeparator = (pathSeparator != null ? pathSeparator : DEFAULT_PATH_SEPARATOR);
    }

    
    public void setTrimTokens(boolean trimTokens) {
        this.trimTokens = trimTokens;
    }

    
    public void setCachePatterns(boolean cachePatterns) {
        this.cachePatterns = cachePatterns;
    }


    @Override
    public boolean isPattern(String path) {
        return (path.indexOf('*') != -1 || path.indexOf('?') != -1);
    }

    @Override
    public boolean match(String pattern, String path) {
        return doMatch(pattern, path, true, null);
    }

    @Override
    public boolean matchStart(String pattern, String path) {
        return doMatch(pattern, path, false, null);
    }

    
    protected boolean doMatch(String pattern, String path, boolean fullMatch, Map<String, String> uriTemplateVariables) {
        if (path.startsWith(this.pathSeparator) != pattern.startsWith(this.pathSeparator)) {
            return false;
        }

        String[] pattDirs = StringUtils.tokenizeToStringArray(pattern, this.pathSeparator, this.trimTokens, true);
        String[] pathDirs = StringUtils.tokenizeToStringArray(path, this.pathSeparator, this.trimTokens, true);

        int pattIdxStart = 0;
        int pattIdxEnd = pattDirs.length - 1;
        int pathIdxStart = 0;
        int pathIdxEnd = pathDirs.length - 1;

        // Match all elements up to the first **
        while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
            String pattDir = pattDirs[pattIdxStart];
            if ("**".equals(pattDir)) {
                break;
            }
            if (!matchStrings(pattDir, pathDirs[pathIdxStart], uriTemplateVariables)) {
                return false;
            }
            pattIdxStart++;
            pathIdxStart++;
        }

        if (pathIdxStart > pathIdxEnd) {
            // Path is exhausted, only match if rest of pattern is * or **'s
            if (pattIdxStart > pattIdxEnd) {
                return (pattern.endsWith(this.pathSeparator) ? path.endsWith(this.pathSeparator) :
                        !path.endsWith(this.pathSeparator));
            }
            if (!fullMatch) {
                return true;
            }
            if (pattIdxStart == pattIdxEnd && pattDirs[pattIdxStart].equals("*") && path.endsWith(this.pathSeparator)) {
                return true;
            }
            for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
                if (!pattDirs[i].equals("**")) {
                    return false;
                }
            }
            return true;
        } else if (pattIdxStart > pattIdxEnd) {
            // String not exhausted, but pattern is. Failure.
            return false;
        } else if (!fullMatch && "**".equals(pattDirs[pattIdxStart])) {
            // Path start definitely matches due to "**" part in pattern.
            return true;
        }

        // up to last '**'
        while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
            String pattDir = pattDirs[pattIdxEnd];
            if (pattDir.equals("**")) {
                break;
            }
            if (!matchStrings(pattDir, pathDirs[pathIdxEnd], uriTemplateVariables)) {
                return false;
            }
            pattIdxEnd--;
            pathIdxEnd--;
        }
        if (pathIdxStart > pathIdxEnd) {
            // String is exhausted
            for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
                if (!pattDirs[i].equals("**")) {
                    return false;
                }
            }
            return true;
        }

        while (pattIdxStart != pattIdxEnd && pathIdxStart <= pathIdxEnd) {
            int patIdxTmp = -1;
            for (int i = pattIdxStart + 1; i <= pattIdxEnd; i++) {
                if (pattDirs[i].equals("**")) {
                    patIdxTmp = i;
                    break;
                }
            }
            if (patIdxTmp == pattIdxStart + 1) {
                // '**/**' situation, so skip one
                pattIdxStart++;
                continue;
            }
            // Find the pattern between padIdxStart & padIdxTmp in str between
            // strIdxStart & strIdxEnd
            int patLength = (patIdxTmp - pattIdxStart - 1);
            int strLength = (pathIdxEnd - pathIdxStart + 1);
            int foundIdx = -1;

            strLoop:
            for (int i = 0; i <= strLength - patLength; i++) {
                for (int j = 0; j < patLength; j++) {
                    String subPat = pattDirs[pattIdxStart + j + 1];
                    String subStr = pathDirs[pathIdxStart + i + j];
                    if (!matchStrings(subPat, subStr, uriTemplateVariables)) {
                        continue strLoop;
                    }
                }
                foundIdx = pathIdxStart + i;
                break;
            }

            if (foundIdx == -1) {
                return false;
            }

            pattIdxStart = patIdxTmp;
            pathIdxStart = foundIdx + patLength;
        }

        for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
            if (!pattDirs[i].equals("**")) {
                return false;
            }
        }

        return true;
    }

    
    private boolean matchStrings(String pattern, String str, Map<String, String> uriTemplateVariables) {
        return getStringMatcher(pattern).matchStrings(str, uriTemplateVariables);
    }

    
    protected AntPathStringMatcher getStringMatcher(String pattern) {
        AntPathStringMatcher matcher = null;
        Boolean cachePatterns = this.cachePatterns;
        if (cachePatterns == null || cachePatterns.booleanValue()) {
            matcher = this.stringMatcherCache.get(pattern);
        }
        if (matcher == null) {
            matcher = new AntPathStringMatcher(pattern);
            if (cachePatterns == null && this.stringMatcherCache.size() >= CACHE_TURNOFF_THRESHOLD) {
                // Try to adapt to the runtime situation that we're encountering:
                // There are obviously too many different paths coming in here...
                // So let's turn off the cache since the patterns are unlikely to be reoccurring.
                this.cachePatterns = false;
                this.stringMatcherCache.clear();
                return matcher;
            }
            if (cachePatterns == null || cachePatterns.booleanValue()) {
                this.stringMatcherCache.put(pattern, matcher);
            }
        }
        return matcher;
    }

    
    @Override
    public String extractPathWithinPattern(String pattern, String path) {
        String[] patternParts = StringUtils.tokenizeToStringArray(pattern, this.pathSeparator, this.trimTokens, true);
        String[] pathParts = StringUtils.tokenizeToStringArray(path, this.pathSeparator, this.trimTokens, true);

        StringBuilder builder = new StringBuilder();

        // Add any path parts that have a wildcarded pattern part.
        int puts = 0;
        for (int i = 0; i < patternParts.length; i++) {
            String patternPart = patternParts[i];
            if ((patternPart.indexOf('*') > -1 || patternPart.indexOf('?') > -1) && pathParts.length >= i + 1) {
                if (puts > 0 || (i == 0 && !pattern.startsWith(this.pathSeparator))) {
                    builder.append(this.pathSeparator);
                }
                builder.append(pathParts[i]);
                puts++;
            }
        }

        // Append any trailing path parts.
        for (int i = patternParts.length; i < pathParts.length; i++) {
            if (puts > 0 || i > 0) {
                builder.append(this.pathSeparator);
            }
            builder.append(pathParts[i]);
        }

        return builder.toString();
    }

    @Override
    public Map<String, String> extractUriTemplateVariables(String pattern, String path) {
        Map<String, String> variables = new LinkedHashMap<String, String>();
        boolean result = doMatch(pattern, path, true, variables);
        Assert.state(result, "Pattern \"" + pattern + "\" is not a match for \"" + path + "\"");
        return variables;
    }

    
    @Override
    public String combine(String pattern1, String pattern2) {
        if (!StringUtils.hasText(pattern1) && !StringUtils.hasText(pattern2)) {
            return "";
        }
        if (!StringUtils.hasText(pattern1)) {
            return pattern2;
        }
        if (!StringUtils.hasText(pattern2)) {
            return pattern1;
        }

        boolean pattern1ContainsUriVar = pattern1.indexOf('{') != -1;
        if (!pattern1.equals(pattern2) && !pattern1ContainsUriVar && match(pattern1, pattern2)) {
            // /* + /hotel -> /hotel ; "/*.*" + "/*.html" -> /*.html
            // However /user + /user -> /usr/user ; /{foo} + /bar -> /{foo}/bar
            return pattern2;
        }

        // /hotels/* + /booking -> /hotels/booking
        // /hotels/* + booking -> /hotels/booking
        if (pattern1.endsWith("/*")) {
            return slashConcat(pattern1.substring(0, pattern1.length() - 2), pattern2);
        }

        // /hotelsbooking
        // /hotelsbooking
        if (pattern1.endsWith("/**")) {
            return slashConcat(pattern1, pattern2);
        }

        int starDotPos1 = pattern1.indexOf("*.");
        if (pattern1ContainsUriVar || starDotPos1 == -1) {
            // simply concatenate the two patterns
            return slashConcat(pattern1, pattern2);
        }
        String extension1 = pattern1.substring(starDotPos1 + 1);
        int dotPos2 = pattern2.indexOf('.');
        String fileName2 = (dotPos2 == -1 ? pattern2 : pattern2.substring(0, dotPos2));
        String extension2 = (dotPos2 == -1 ? "" : pattern2.substring(dotPos2));
        String extension = extension1.startsWith("*") ? extension2 : extension1;
        return fileName2 + extension;
    }

    private String slashConcat(String path1, String path2) {
        if (path1.endsWith("/") || path2.startsWith("/")) {
            return path1 + path2;
        }
        return path1 + "/" + path2;
    }

    
    @Override
    public Comparator<String> getPatternComparator(String path) {
        return new AntPatternComparator(path);
    }


    
    protected static class AntPathStringMatcher {

        private static final Pattern GLOB_PATTERN = Pattern.compile("\\?|\\*|\\{((?:\\{[^/]+?\\}|[^/{}]|\\\\[{}])+?)\\}");

        private static final String DEFAULT_VARIABLE_PATTERN = "(.*)";

        private final Pattern pattern;

        private final List<String> variableNames = new LinkedList<String>();

        public AntPathStringMatcher(String pattern) {
            StringBuilder patternBuilder = new StringBuilder();
            Matcher m = GLOB_PATTERN.matcher(pattern);
            int end = 0;
            while (m.find()) {
                patternBuilder.append(quote(pattern, end, m.start()));
                String match = m.group();
                if ("?".equals(match)) {
                    patternBuilder.append('.');
                } else if ("*".equals(match)) {
                    patternBuilder.append(".*");
                } else if (match.startsWith("{") && match.endsWith("}")) {
                    int colonIdx = match.indexOf(':');
                    if (colonIdx == -1) {
                        patternBuilder.append(DEFAULT_VARIABLE_PATTERN);
                        this.variableNames.add(m.group(1));
                    } else {
                        String variablePattern = match.substring(colonIdx + 1, match.length() - 1);
                        patternBuilder.append('(');
                        patternBuilder.append(variablePattern);
                        patternBuilder.append(')');
                        String variableName = match.substring(1, colonIdx);
                        this.variableNames.add(variableName);
                    }
                }
                end = m.end();
            }
            patternBuilder.append(quote(pattern, end, pattern.length()));
            this.pattern = Pattern.compile(patternBuilder.toString());
        }

        private String quote(String s, int start, int end) {
            if (start == end) {
                return "";
            }
            return Pattern.quote(s.substring(start, end));
        }

        
        public boolean matchStrings(String str, Map<String, String> uriTemplateVariables) {
            Matcher matcher = this.pattern.matcher(str);
            if (matcher.matches()) {
                if (uriTemplateVariables != null) {
                    // SPR-8455
                    Assert.isTrue(this.variableNames.size() == matcher.groupCount(),
                            "The number of capturing groups in the pattern segment " + this.pattern +
                                    " does not match the number of URI template variables it defines, which can occur if " +
                                    " capturing groups are used in a URI template regex. Use non-capturing groups instead."
                    );
                    for (int i = 1; i <= matcher.groupCount(); i++) {
                        String name = this.variableNames.get(i - 1);
                        String value = matcher.group(i);
                        uriTemplateVariables.put(name, value);
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }


    
    protected static class AntPatternComparator implements Comparator<String> {

        private final String path;

        public AntPatternComparator(String path) {
            this.path = path;
        }

        @Override
        public int compare(String pattern1, String pattern2) {
            if (isNullOrCaptureAllPattern(pattern1) && isNullOrCaptureAllPattern(pattern2)) {
                return 0;
            } else if (isNullOrCaptureAllPattern(pattern1)) {
                return 1;
            } else if (isNullOrCaptureAllPattern(pattern2)) {
                return -1;
            }

            boolean pattern1EqualsPath = pattern1.equals(path);
            boolean pattern2EqualsPath = pattern2.equals(path);
            if (pattern1EqualsPath && pattern2EqualsPath) {
                return 0;
            } else if (pattern1EqualsPath) {
                return -1;
            } else if (pattern2EqualsPath) {
                return 1;
            }

            int wildCardCount1 = getWildCardCount(pattern1);
            int wildCardCount2 = getWildCardCount(pattern2);

            int bracketCount1 = StringUtils.countOccurrencesOf(pattern1, "{");
            int bracketCount2 = StringUtils.countOccurrencesOf(pattern2, "{");

            int totalCount1 = wildCardCount1 + bracketCount1;
            int totalCount2 = wildCardCount2 + bracketCount2;

            if (totalCount1 != totalCount2) {
                return totalCount1 - totalCount2;
            }

            int pattern1Length = getPatternLength(pattern1);
            int pattern2Length = getPatternLength(pattern2);

            if (pattern1Length != pattern2Length) {
                return pattern2Length - pattern1Length;
            }

            if (wildCardCount1 < wildCardCount2) {
                return -1;
            } else if (wildCardCount2 < wildCardCount1) {
                return 1;
            }

            if (bracketCount1 < bracketCount2) {
                return -1;
            } else if (bracketCount2 < bracketCount1) {
                return 1;
            }

            return 0;
        }

        private boolean isNullOrCaptureAllPattern(String pattern) {
            return pattern == null || "/**".equals(pattern);
        }

        private int getWildCardCount(String pattern) {
            if (pattern.endsWith(".*")) {
                pattern = pattern.substring(0, pattern.length() - 2);
            }
            return StringUtils.countOccurrencesOf(pattern, "*");
        }

        
        private int getPatternLength(String pattern) {
            return VARIABLE_PATTERN.matcher(pattern).replaceAll("#").length();
        }
    }

}
