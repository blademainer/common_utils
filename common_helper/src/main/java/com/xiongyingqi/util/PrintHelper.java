package com.xiongyingqi.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/9/17 0017.
 */
public class PrintHelper {
    private Map<String, String> commentMap;
    private Collection<String> ignoreProperties;
    private String commentPattern = "{0}<{1}>";
    private String ignorePattern = "({0}=.*?,\\s*)|({0}=.*?])";
    private Pattern pattern = Pattern.compile("password=.*((,\\s)|])*");

    public static PrintHelper needComment() {
        PrintHelper printHelper = new PrintHelper();
        printHelper.commentMap = new HashMap<String, String>();
        printHelper.ignoreProperties = new ArrayList<String>();
        return printHelper;
    }

    public PrintHelper addComment(String word, String comment) {
        commentMap.put(word, comment);
        return this;
    }

    public PrintHelper ignoreProperty(String propertyName) {
        ignoreProperties.add(propertyName);
        return this;
    }

    private String buildContent(String content) {
        Set<Map.Entry<String, String>> entries = commentMap.entrySet();
        StringBuilder builder = new StringBuilder(content);

//        for (String ignoreProperty : ignoreProperties) {
//            handleIgnoreProperty(builder, ignoreProperty);
//        }

        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            replaceWord(builder, key, value);
        }
        return builder.toString();
    }

    private void handleIgnoreProperty(StringBuilder builder, String propertyName) {
        String s = builder.toString();
        String patternStr = MessageFormat.format(ignorePattern, propertyName);
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(builder);
        while (matcher.find()) {
            s = matcher.replaceAll("");
        }

        builder.replace(0, builder.length(), s);
    }

    private String buildWordAndComment(String word, String comment) {
        String rs = MessageFormat.format(commentPattern, word, comment);
        return rs;
    }

    private void replaceWord(StringBuilder builder, String word, String comment) {
        String replacement = buildWordAndComment(word, comment);
        int fromIndex = 0;
        int index;
        while ((index = builder.indexOf(word, fromIndex)) > 0) {
            fromIndex = index + 1;
            builder.replace(index, index + word.length(), replacement);
        }
    }

    /**
     * 打印对象同时加入注释
     *
     * @param object
     */
    public void printC(Object object) {
        StackTraceElement[] stackTraceElements = StackTraceHelper.getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[2]; // 调用本类的对象类型堆栈
        StringBuilder builder = new StringBuilder();
        builder.append(" ------------------------------------------------------------ ");
        builder.append(StringHelper.line());
        builder.append(StackTraceHelper
                .buildStackTrace(new StackTraceElement[]{stackTraceElement}));
        builder.append("    ");
        if (object == null) {
            builder.append("<null>");
        } else {
            builder.append(object.getClass().getSimpleName());
            builder.append(" =============== ");
            String content = buildObjectToString(object);
            builder.append(buildContent(content));
        }
        builder.append(StringHelper.line());
        builder.append(" ------------------------------------------------------------ ");
        System.out.println(builder.toString());
    }

    private String buildObjectToString(Object object) {
        if (object == null) {
            return null;
        }

        Class clazz = object.getClass();

        StringBuilder builder = new StringBuilder();
        if (clazz.isArray()) {
            builder.append("[");
            int length = Array.getLength(object);
            for (int i = 0; i < length; i++) {
                Object one = Array.get(object, i);
                builder.append(buildObjectToString(one));
                if (i < length - 1) {
                    builder.append(", ");
                }
            }
            builder.append("]");
//        } else if (clazz.getSuperclass() == Collection.class) {
//            builder.append("[");
//            Collection collection = (Collection) object;
//
//            int i = 0;
//            int length = collection.size();
//            for (Object o : collection) {
//                builder.append(buildObjectToString(o));
//                if (i < length - 1) {
//                    builder.append(", ");
//                }
//            }
//            builder.append("]");
        } else {
            builder.append(reflectToString(object));
        }
        return builder.toString();
    }


    /**
     * 使用反射机制自动ToString <br>
     * 2013-8-28 下午3:16:40
     *
     * @param object
     * @return
     */
    public String reflectToString(Object object) {
        if (object == null) {
            return null;
        }
        Class clazz = object.getClass();

        StringBuilder builder = new StringBuilder(clazz.getName());
        builder.append("@");
        builder.append(Integer.toHexString(object.hashCode()));
        builder.append("[");

        Set<Method> methods = new LinkedHashSet<Method>();

        Collection<Class> classes = EntityHelper.getAllSuperClassesOfClass(clazz);
        for (Iterator iterator = classes.iterator(); iterator.hasNext(); ) {
            Class claxx = (Class) iterator.next();
            Method[] clazzMethods = claxx.getDeclaredMethods();
            for (int i = 0; i < clazzMethods.length; i++) {
                Method method = clazzMethods[i];
                methods.add(method);
            }
        }

        // for (int i = 0; i < methods.length; i++) {
        // Method method = methods[i];
        for (Iterator iterator = methods.iterator(); iterator.hasNext(); ) {
            Method method = (Method) iterator.next();
            String methodName = method.getName();
            if (methodName.startsWith("get") && !Modifier.isStatic(method.getModifiers())
                    && Modifier.isPublic(method.getModifiers())) {
                try {
                    Object value = method.invoke(object);
                    String propertyName = methodName.substring(3, 4).toLowerCase()
                            + methodName.substring(4);
                    if (propertyName.equals("class") || ignoreProperties.contains(propertyName)) {// 忽略getClass方法
                        continue;
                    }
                    builder.append(propertyName);
                    builder.append("=");

                    if (value == null) {
                        builder.append("<null>");
                    } else {
                        if (value.getClass().isArray()) {
                            int arraySuperLength = Array.getLength(value);
                            builder.append("{");
                            for (int j = 0; j < arraySuperLength; j++) {
                                Object object2 = Array.get(value, j);
                                builder.append(object2.toString());
                                if (j < arraySuperLength - 1) {
                                    builder.append(", ");
                                }
                            }
                            builder.append("}");
                        } else {
                            builder.append(value.toString());
                        }
                    }
                    builder.append(", ");
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                }
            }
        }

        if (builder.toString().contains(", ")) {
            builder.replace(builder.length() - 2, builder.length(), "");
        }

        builder.append("]");
        return builder.toString();
    }

    /**
     * 打印对象详情 <br>
     * 2013-8-26 下午2:45:02
     *
     * @param object
     */
    public static void printDetail(Object object) {
        StackTraceElement[] stackTraceElements = StackTraceHelper.getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[2]; // 调用本类的对象类型堆栈
        StringBuilder builder = new StringBuilder();
        builder.append(" ------------------------------------------------------------ ");
        builder.append(StringHelper.line());
        builder.append(StackTraceHelper
                .buildStackTrace(new StackTraceElement[]{stackTraceElement}));
        builder.append("    ");
        if (object == null) {
            builder.append("<null>");
        } else {
            builder.append(object.getClass().getSimpleName());
            builder.append(" =============== ");
            builder.append(EntityHelper.reflectToString(object));
        }
        builder.append(StringHelper.line());
        builder.append(" ------------------------------------------------------------ ");
        System.out.println(builder.toString());
    }

    /**
     * 打印对象， <br>
     * 2013-8-26 下午2:45:02
     *
     * @param object
     */
    public static void print(Object object) {
        StackTraceElement[] stackTraceElements = StackTraceHelper.getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[2]; // 调用本类的对象类型堆栈
        StringBuilder builder = new StringBuilder();
        builder.append(" ------------------------------------------------------------ ");
        builder.append(StringHelper.line());
        builder.append(StackTraceHelper
                .buildStackTrace(new StackTraceElement[]{stackTraceElement}));
        builder.append("    ");
        if (object == null) {
            builder.append("<null>");
        } else {
            builder.append(object.getClass().getSimpleName());
            builder.append(" =============== ");
            builder.append(EntityHelper.buildObjectToString(object));
        }
        builder.append(StringHelper.line());
        builder.append(" ------------------------------------------------------------ ");
        System.out.println(builder.toString());
    }


    public static void print() {
        StackTraceElement[] stackTraceElements = StackTraceHelper.getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[2]; // 调用本类的对象类型堆栈
        StringBuilder builder = new StringBuilder();
        builder.append(" ------------------------------------------------------------ ");
        builder.append(StringHelper.line());
        builder.append(StackTraceHelper
                .buildStackTrace(new StackTraceElement[]{stackTraceElement}));
        builder.append("    ");
        builder.append(StringHelper.line());
        builder.append(" ------------------------------------------------------------ ");
        System.out.println(builder.toString());
    }
}
