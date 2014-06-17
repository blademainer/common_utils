package com.xiongyingqi.utils.code;

import com.xiongyingqi.util.EntityHelper;
import com.xiongyingqi.util.ThreadPool;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码组建类
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/3/31 0031.
 */
public class CodeBuilder {

    private Pattern include;

    private Pattern exclude;

    /**
     * 基础类
     */
    private File baseFile;
    /**
     * 继承类
     */
    private String superClass;
    /**
     * 实现接口
     */
    private Set<String> interfaces = new HashSet<String>();
    /**
     * 代码块
     */
    private Set<String> codeFragments = new HashSet<String>();

    private Set<String> imports = new HashSet<String>();
    private Set<String> removeImports = new HashSet<String>();

    private Set<File> files = new HashSet<File>();

    /**
     * 标志是否删除原有的继承类
     *
     * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
     * @version 2014/3/20 0020 18:18
     */
    private boolean removeSuperClass;

    /**
     * 标识是否删除所有注解
     */
    private boolean removeAnnotations;

    public CodeBuilder() {

    }

    public CodeBuilder(String folderPath) {
        this(new File(folderPath));
    }

    public CodeBuilder(File file) {
        if (file != null && file.exists()) {
            on(file);
        } else {
            System.out.println("文件：" + file + "不存在！");
        }
    }

    public CodeBuilder on(String folderPath) {
        return on(new File(folderPath));
    }


    public CodeBuilder on(File[] files) {
        for (File file : files) {
            scanFiles(file);
        }
        return this;
    }

    public CodeBuilder on(File file) {
        this.baseFile = file;
        scanFiles(file);
        return this;
    }

    private void scanFiles(File file) {
        if (file.isFile()) {
            this.files.add(file);
        } else {
            File[] files = file.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if (name.endsWith(".java")) {
                        return true;
                    }
                    return false;
                }
            });
            for (File fileVar : files) {
                this.files.add(fileVar);
            }
        }
    }

    /**
     * 排除文件名，必须使用正则表达式语法
     *
     * @param regex 表达式
     * @return CodeBuilder
     */
    public CodeBuilder exclude(String regex) {
        this.exclude = Pattern.compile(regex);

//            Set<File> toExcludes = new HashSet<File>();
//
//            Pattern pattern = Pattern.compile(regex);
//            for (File file : files) {
//                Matcher matcher = pattern.matcher(file.getPath());
//                if (matcher.find()) {
//                    toExcludes.add(file);
//                }
//            }
//            files.removeAll(toExcludes);
        return this;
    }

    /**
     * 包含文件名，必须使用正则表达式语法
     *
     * @param regex 正则表达式
     * @return CodeBuilder
     */
    public CodeBuilder include(String regex) {
        this.include = Pattern.compile(regex);

//            Set<File> toIncludes = new HashSet<File>();
//
//            Pattern pattern = Pattern.compile(regex);
//            for (File file : files) {
//                Matcher matcher = pattern.matcher(file.getPath());
//                if (matcher.find()) {
//                    toIncludes.add(file);
//                }
//            }
//            this.files = toIncludes;
        return this;
    }

    /**
     * 设置继承类
     *
     * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
     * @version 2014/3/20 0020 19:57
     */
    public CodeBuilder superClass(Class<?> clazz) {
        if (clazz.isInterface()) {
            System.out.println(clazz + "为接口对象！取消设为继承类。");
            return this;
        }

        String simpleName = clazz.getSimpleName();
        Package pkg = clazz.getPackage();
        imports.add(pkg.getName() + "." + simpleName);// 导入包
        this.superClass = simpleName;// 设置继承的类名
        return this;
    }

    /**
     * 增加接口
     *
     * @param interfaceClass
     * @return
     */
    public CodeBuilder addInterface(Class<?> interfaceClass) {
        if (!interfaceClass.isInterface()) {
            System.out.println(interfaceClass + "不是接口类型！取消设为实现。");
            return this;
        }
        String simpleName = interfaceClass.getSimpleName();
        Package pkg = interfaceClass.getPackage();
        imports.add(pkg.getName() + "." + simpleName);// 导入包
        this.interfaces.add(simpleName);// 添加实现的接口
        return this;
    }

    /**
     * 增加代码段，可以是代码片段、方法、字段等，但是必须要加入代码段内的导入包
     *
     * @param codeFragment
     * @return
     */
    public CodeBuilder addCodeFragment(String codeFragment) {
        codeFragments.add(codeFragment);
        return this;
    }

    /**
     * 增加导入包，必须不加<pre>import </pre>
     *
     * @param impt
     * @return
     */
    public CodeBuilder addImport(String impt) {
        this.imports.add(impt);
        return this;
    }

    public CodeBuilder removeImport(String impt) {
        this.removeImports.add(impt);
        return this;
    }

    /**
     * 过滤包含规则
     */
    private void filterInclude() {
        if (include == null || files == null) {
            return;
        }

        Set<File> toIncludes = new HashSet<File>();

        for (File file : files) {
            Matcher matcher = include.matcher(file.getPath());
            if (matcher.find()) {
                toIncludes.add(file);
            }
        }
        this.files = toIncludes;
    }

    /**
     * 过滤排除规则
     */
    private void filterExclude() {
        if (exclude == null || files == null) {
            return;
        }

        Set<File> toExcludes = new HashSet<File>();

        for (File file : files) {
            Matcher matcher = exclude.matcher(file.getPath());
            if (matcher.find()) {
                toExcludes.add(file);
            }
        }
        files.removeAll(toExcludes);
    }

    /**
     * 移除原有的继承类，superClass(Class)方法默认使用该参数
     *
     * @return
     */
    public CodeBuilder removeSuperClass() {
        this.removeSuperClass = true;
        return this;
    }

    public CodeBuilder removeAnnotations() {
        this.removeAnnotations = true;
        return this;
    }

    /**
     * 开始重写文件
     */
    public void build() {
        filterInclude();
        filterExclude();

        for (final File file : files) {
            ThreadPool.invoke(new Thread() {
                @Override
                public void run() {
                    new CodeProcessor(file, CodeBuilder.this);
                }
            }); // 在线程池内运行
        }

        ThreadPool.shutDown();// 开启停止线程
    }


    public File getBaseFile() {
        return baseFile;
    }

    public Set<String> getInterfaces() {
        return interfaces;
    }

    public Set<String> getImports() {
        return imports;
    }

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public Set<File> getFiles() {
        return files;
    }

    public boolean isRemoveSuperClass() {
        return removeSuperClass;
    }

    public Set<String> getCodeFragments() {
        return codeFragments;
    }

    public Pattern getInclude() {
        return include;
    }

    public Pattern getExclude() {
        return exclude;
    }


    public boolean isRemoveAnnotations() {
        return removeAnnotations;
    }


    public Set<String> getRemoveImports() {
        return removeImports;
    }

    @Override
    public String toString() {
        return EntityHelper.reflectToString(this);
    }

    public static void main(String[] args) {
        CodeHelper.newCodeBuilder("D:\\workspace_JavaEE\\IWasHere\\IWasHere_ENTITY\\src\\main\\domainbak")
                .removeAnnotations().removeImport("javax.persistence.*").build();
    }
}
