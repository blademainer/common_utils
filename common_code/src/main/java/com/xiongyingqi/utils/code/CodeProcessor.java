package com.xiongyingqi.utils.code;

import com.xiongyingqi.util.EntityHelper;
import com.xiongyingqi.util.FileHelper;
import com.xiongyingqi.util.StringHelper;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/3/31 0031.
 */
public class CodeProcessor {

    /**
     * 查找类、继承类、实现接口的定义
     */
    private final Pattern PATTERN_FIND_CLASS_SEGMENT = Pattern.compile("((public|private|protected)[\\s]+)?[\\s]*class[\\s]*[\\w\\d]+[\\s]*([\\s]+extends[\\s]+[\\w\\d]+)?[\\s]*([\\s]+implements[\\s]+[\\w\\d]+(\\,[\\s]*[\\w\\d]+[\\s]*)*)?[\\s]*\\{");

    /**
     * 查找类定义
     */
    private final Pattern PATTERN_FIND_CLASS_NAME = Pattern.compile("((public|private|protected)[\\s]+)?[\\s]*class[\\s]*[\\w\\d]+[\\s]*");
    /**
     * 查找class+类名
     */
    private final Pattern PATTERN_FIND_CLASS_NAME_WITH_CLASS = Pattern.compile("class[\\s]+[\\w\\d]+");


    /**
     * 查找接口定义
     */
    private final Pattern PATTERN_FIND_INTERCES = Pattern.compile("implements[\\s]+[\\w\\d]+(\\,[\\s]*[\\w\\d]+[\\s]*)*");

    /**
     * 查找继承类定义
     */
    private final Pattern PATTERN_FIND_SUPER_CLASS = Pattern.compile("extends[\\s]+[\\w\\d]+");
    /**
     * 查找包定义
     */
    private final Pattern PATTERN_FIND_PACKAGE = Pattern.compile("package[\\s]+[\\w\\d]+(\\.[\\w\\d]+)*\\;");

    /**
     * 查找包定义的名称
     */
    private final Pattern PATTERN_FIND_PACKAGE_NAME = Pattern.compile("[\\w\\d]+(\\.[\\w\\d]+)*");

    /**
     * 查找导入的定义
     */
    private final Pattern PATTERN_FIND_IMPORT = Pattern.compile("import[\\s]+[\\w\\d]+(\\.([\\w\\d]+|[\\*]+)+)*\\;");
    /**
     * 查找导入的定义的名称
     */
    private final Pattern PATTERN_FIND_IMPORT_NAME = Pattern.compile("[\\w\\d]+(\\.([\\w\\d]+|[\\*]+)+)*");

    /**
     * 查找注解
     */
    private final Pattern PATTERN_FIND_ANNOTAION = Pattern.compile("@\\w+(\\(.*?\\)){0,1}");


    private File file;
    private String content;
    private CodeBuilder builder;

    public CodeProcessor(File file, CodeBuilder builder) {
        this.file = file;
        this.builder = builder;
        begin();
    }

    private void begin() {
        if (file == null) {
            return;
        }
        content = readFile(this.file);

        checkClass();
        checkImport();
        checkCodeFragment();
        checkAnnotations();
        removeImport();
//                EntityHelper.print(content);
        writeFile();
        System.out.println("文件：\"" + file + "\"处理完成。");

    }

    private String readFile(File file) {
        return FileHelper.readFileToString(file);
    }

    /**
     * 刷新文件内容
     */
    private void writeFile() {
        FileHelper.writeStringToFile(file, content);
    }

    // ---------------------------------------- 包定义和导入包 ----------------------------------------
    private String findPackage() {
        Matcher matcher = PATTERN_FIND_PACKAGE.matcher(content);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private String findPackageName(String packageDefine) {
        Matcher matcher = PATTERN_FIND_PACKAGE_NAME.matcher(packageDefine.substring("package".length()));
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private Set<String> findImports(String content) {
        Set<String> knowImports = new HashSet<String>();
        Matcher matcher = PATTERN_FIND_IMPORT.matcher(content);
        while (matcher.find()) {
            String found = matcher.group().substring("import".length());
            Matcher matcherImportName = PATTERN_FIND_IMPORT_NAME.matcher(found);
            if (matcherImportName.find()) {
                knowImports.add(matcherImportName.group());
            }
        }
        return knowImports;
    }

    /**
     * 检查导入包
     *
     * @return
     */
    private void checkImport() {
        Set<String> classImports = new HashSet<String>();
        classImports.addAll(builder.getImports());

        classImports.removeAll(findImports(content));// 移除已经导入的包

        Set<String> classImportsToRemove = new HashSet<String>();// 如果要导入的包与类所在的package一致，则忽略导入

        final String packageStr = findPackage();// 类所定义的包路径
        if (packageStr != null) {//如果定义了包路径，则在包路径后面新增导入
            String packageName = findPackageName(packageStr);
            for (String impt : classImports) {
                int point = impt.lastIndexOf(".");
                String pkgNameOfImport = impt.substring(0, point);//截取import的类所属包
                if (pkgNameOfImport.equals(packageName)) {// 如果包已经导入，则取消导入
                    classImportsToRemove.add(impt);
                }
            }
            classImports.removeAll(classImportsToRemove);

            //如果定义了包路径，则在包路径后面新增导入
            StringBuilder builder = new StringBuilder(packageStr);
            builder.append(StringHelper.line());
            for (String toImport : classImports) {
                builder.append(StringHelper.line());
                builder.append("import ");
                builder.append(toImport);
                builder.append(";");
            }
            content = StringHelper.replaceFirst(content, packageStr, builder.toString());
        } else {// 如果没有定义包，则在头部导入包
            StringBuilder builder = new StringBuilder();
            for (String toImport : classImports) {
                builder.append(StringHelper.line());
                builder.append("import ");
                builder.append(toImport);
                builder.append(";");
            }
            content = builder.append(content).toString();
        }

    }

    // ---------------------------------------- 包定义和导入包 ----------------------------------------


    // ---------------------------------------- class片段 ----------------------------------------

    /**
     * 检查class定义段
     */
    private void checkClass() {
        Matcher findClassMatcher = PATTERN_FIND_CLASS_SEGMENT.matcher(content);

        if (findClassMatcher.find()) {
            final String classDefine = findClassMatcher.group();// 查找类定义

            String classDefineReplace = checkSuperClass(classDefine);
            classDefineReplace = checkInterfaces(classDefineReplace);
            content = StringHelper.replaceFirst(content, classDefine, classDefineReplace);
        }
    }

    /**
     * 查找已有的接口定义
     *
     * @param interfaceDefineString 接口定义字符串
     * @return
     */
    private Set<String> findInterfaces(String interfaceDefineString) {
        String interfaceStrings = StringHelper.replaceFirst(interfaceDefineString, "implements", "");
        String[] interfaces = interfaceStrings.split(",");
        Set<String> stringSet = new HashSet<String>();
        for (String interfaceString : interfaces) {
            stringSet.add(interfaceString.trim());
        }
        return stringSet;
    }

    /**
     * 检查implements关键字
     *
     * @param classSegment
     * @return
     */
    private String checkInterfaces(String classSegment) {
        if (builder.getInterfaces() == null || builder.getInterfaces().size() == 0) {
            return classSegment;
        }

        Matcher matcher = PATTERN_FIND_INTERCES.matcher(classSegment);

        if (matcher.find()) {
            final String interfaceDefineString = matcher.group();

            Set<String> interfaceSet = findInterfaces(interfaceDefineString);


            StringBuilder interfaceDefineStringReplace = new StringBuilder(interfaceDefineString);
            for (String interfaceName : builder.getInterfaces()) {
                if (interfaceSet.contains(interfaceName)) {// 如果已经存在接口的定义，则跳过该定义
                    continue;
                }
                interfaceDefineStringReplace.append(", ");
                interfaceDefineStringReplace.append(interfaceName);
            }
            classSegment = StringHelper.replaceFirst(classSegment, interfaceDefineString, interfaceDefineStringReplace.toString());
        } else {
            final String interfaceDefineString = "{";
            StringBuilder interfaceDefineStringReplace = new StringBuilder("implements ");
            boolean isFirstInterface = true;
            for (String interfaceName : builder.getInterfaces()) {
                if (!isFirstInterface) {
                    interfaceDefineStringReplace.append(", ");
                }
                if (isFirstInterface) {
                    isFirstInterface = false;
                }

                interfaceDefineStringReplace.append(interfaceName);
            }
            interfaceDefineStringReplace.append(interfaceDefineString);
            classSegment = StringHelper.replaceFirst(classSegment, interfaceDefineString, interfaceDefineStringReplace.toString());
        }
        return classSegment;
    }


    private String findClassName(String classDefine) {
        Matcher classNameMatcher = PATTERN_FIND_CLASS_NAME_WITH_CLASS.matcher(classDefine);
        if (classNameMatcher.find()) {
            String classNameWithClass = classNameMatcher.group();// class ClassName
            return StringHelper.replaceFirst(classNameWithClass, "class", "").trim();
        }
        return null;
    }

    /**
     * 检查extends关键字
     *
     * @param classSegment
     * @return
     */
    private String checkSuperClass(String classSegment) {
        if (builder.isRemoveSuperClass() || builder.getSuperClass() != null) {
            Matcher matcher = PATTERN_FIND_SUPER_CLASS.matcher(classSegment);
            String superClassString = "";
            if (builder.getSuperClass() != null) {
                superClassString = "extends " + builder.getSuperClass() + " ";
            }

            if (matcher.find()) {
                String replacement = matcher.group();
                classSegment = StringHelper.replaceFirst(classSegment, replacement, superClassString);
            } else {
                Matcher classNameMatcher = PATTERN_FIND_CLASS_NAME.matcher(classSegment);
                if (classNameMatcher.find()) {
                    String className = classNameMatcher.group();
                    String justClassName = findClassName(className);
                    if (justClassName != null && justClassName.equals(builder.getSuperClass())) {
                        return classSegment;
                    }
                    classSegment = StringHelper.replaceFirst(classSegment, className, className + superClassString);
                }
            }

        }
        return classSegment;
    }
    // ---------------------------------------- class片段 ----------------------------------------


    // ---------------------------------------- 代码段 ----------------------------------------

    private void checkCodeFragment() {
        Matcher matcher = PATTERN_FIND_CLASS_SEGMENT.matcher(content);
        if (matcher.find()) {
            final String classDefine = matcher.group();
            StringBuilder builder = new StringBuilder(classDefine);
            for (String codeFragment : this.builder.getCodeFragments()) {
                builder.append(StringHelper.line());
                builder.append(codeFragment);
            }
            content = StringHelper.replaceFirst(content, classDefine, builder.toString());
        }
    }
    // ---------------------------------------- 代码段 ----------------------------------------

    // -------------------------- 移除注解 --------------------------
    private void checkAnnotations() {
        if (!builder.isRemoveAnnotations()) {
            return;
        }
        Matcher matcher = PATTERN_FIND_ANNOTAION.matcher(content);
        while (matcher.find()) {
            final String annotation = matcher.group();
//            StringBuilder builder = new StringBuilder(annotation);
//            for (String codeFragment : this.builder.getCodeFragments()) {
//                builder.append(StringHelper.line());
//                builder.append(codeFragment);
//            }
            content = StringHelper.replaceFirst(content, annotation, "");
            EntityHelper.print(content);
        }
    }

    private void removeImport() {
        Set<String> removeImports = builder.getRemoveImports();
        if (removeImports == null || removeImports.size() == 0) {
            return;
        }

        Matcher matcher = PATTERN_FIND_IMPORT.matcher(content);
        Collection<String> found = new HashSet<String>();
        while (matcher.find()) {
            found.add(matcher.group());
//            StringBuilder builder = new StringBuilder(annotation);
//            for (String codeFragment : this.builder.getCodeFragments()) {
//                builder.append(StringHelper.line());
//                builder.append(codeFragment);
//            }
//            content = StringHelper.replaceFirst(content, annotation, "");
//            EntityHelper.print(content);
        }


        for (String s : found) {
            for (String removeImport : removeImports) {
                if(s.contains(removeImport)){
                    content = StringHelper.replaceFirst(content, s, "");
                }
            }
        }
    }

}
