package com.xiongyingqi.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/10/21 0021.
 */
public class PackageScanner {
    private Collection<Package> packages;
    private Collection<Class<? extends Annotation>> orAnnotations;
    private Collection<Class<? extends Annotation>> andAnnotations;
    private Collection<String> startWithStrings;
    private Collection<String> endWithStrings;
    private Collection<Class<?>> orImplementsInterface;
    private Collection<Class<?>> andImplementsInterface;
    private boolean recursive;

    private PackageScanner() {
    }

    public static PackageScanner newScanner() {
        PackageScanner packageScanner = new PackageScanner();
        packageScanner.packages = new ArrayList<Package>();
        packageScanner.orAnnotations = new ArrayList<Class<? extends Annotation>>();
        packageScanner.andAnnotations = new ArrayList<Class<? extends Annotation>>();
        packageScanner.startWithStrings = new ArrayList<String>();
        packageScanner.endWithStrings = new ArrayList<String>();
        packageScanner.orImplementsInterface = new ArrayList<Class<?>>();
        packageScanner.andImplementsInterface = new ArrayList<Class<?>>();

        packageScanner.recursive = true;
        return packageScanner;
    }

    /**
     * 增加扫描的包
     *
     * @param pkg
     * @return
     */
    public PackageScanner addPackage(String pkg) {
        Package aPackage = Package.getPackage(pkg);
        addPackage(aPackage);
        return this;
    }

    /**
     * 增加扫描的包
     *
     * @param pkg
     * @return
     */
    public PackageScanner addPackage(Package pkg) {
        packages.add(pkg);
        return this;
    }

    /**
     * 是否递归子包
     *
     * @param recursive
     */
    public PackageScanner recursive(boolean recursive) {
        this.recursive = recursive;
        return this;
    }

    /**
     * 或者类被annotation注解
     *
     * @return
     */
    public PackageScanner orAnnotation(Class<? extends Annotation> annotation) {
        orAnnotations.add(annotation);
        return this;
    }

    /**
     * 并且类被annotation注解
     *
     * @param annotation
     * @return
     */
    public PackageScanner andAnnotation(Class<? extends Annotation> annotation) {
        andAnnotations.add(annotation);
        return this;
    }

    /**
     * 并且类实现接口
     *
     * @param interfc
     * @return
     */
    public PackageScanner andInterface(Class<?> interfc) {
        andImplementsInterface.add(interfc);
        return this;
    }

    /**
     * 或者类实现接口
     *
     * @param interfc
     * @return
     */
    public PackageScanner orInterface(Class<?> interfc) {
        orImplementsInterface.add(interfc);
        return this;
    }

    /**
     * @param startWithString
     * @return
     */
    public PackageScanner orStartWithString(String startWithString) {
        if (StringHelper.nullOrEmpty(startWithString)) {
            return this;
        }
        startWithStrings.add(startWithString);
        return this;
    }

    public PackageScanner orEndWithString(String endWithString) {
        if (StringHelper.nullOrEmpty(endWithString)) {
            return this;
        }
        endWithStrings.add(endWithString);
        return this;
    }


    /**
     * 开始扫描
     *
     * @return
     */
    public Collection<Class<?>> scan() {
        Assert.notEmpty(packages, "待扫描的包为空！");
        Collection<Class<?>> classes = new ArrayList<Class<?>>();
        ClassLookupHelper.ClassFileFilter classFileFilter = new ClassLookupHelper.ClassFileFilter() {
            @Override
            public boolean accept(String klassName, File file, ClassLoader loader) {
                return true;
            }

            @Override
            public boolean accept(String klassName, JarFile jar, JarEntry entry, ClassLoader loader) {
                return true;
            }
        };
        for (Package pkg : packages) {
            Set<Class<?>> scanClasses = ClassLookupHelper.getClasses(pkg, recursive, classFileFilter);
            for (Class<?> scanClass : scanClasses) {
                if (isAnnotationPassed(scanClass) && isStartWithPassed(scanClass) && isInterfacePassed(scanClass)) {
                    classes.add(scanClass);
                }
            }
        }
        return classes;
    }

    private boolean isStartWithPassed(Class<?> clazz) {
        // 如果是需要判断开始和结尾，则初始值为false
        if (true ^ (CollectionHelper.notNullAndHasSize(startWithStrings) || CollectionHelper.notNullAndHasSize(endWithStrings))) {// 如果需要判断的为空，则通过
            return true;
        }

        for (String startWithString : startWithStrings) {
            if (clazz.getSimpleName().startsWith(startWithString)) {
                return true;
            }
        }

        for (String endWithString : endWithStrings) {
            if (clazz.getSimpleName().endsWith(endWithString)) {
                return true;
            }
        }

        return false;
    }

    private boolean isAnnotationPassed(Class<?> clazz) {
        //true ^ true = false, true ^ false = true
        boolean flag = true ^ (CollectionHelper.notNullAndHasSize(orAnnotations) || CollectionHelper.notNullAndHasSize(andAnnotations));// 如果判断是否需要注解，则初始值为false

        for (Class<? extends Annotation> orAnnotation : orAnnotations) {
            boolean annotationPresent = clazz.isAnnotationPresent(orAnnotation);
            if (annotationPresent) {
                flag = true;
                break;
            }
        }


        for (Class<? extends Annotation> andAnnotation : andAnnotations) {
            boolean annotationPresent = clazz.isAnnotationPresent(andAnnotation);
            if (!annotationPresent) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private boolean isInterfacePassed(Class<?> clazz) {
        //true ^ true = false, true ^ false = true
        boolean flag = true ^ (CollectionHelper.notNullAndHasSize(orImplementsInterface) || CollectionHelper.notNullAndHasSize(andImplementsInterface));// 如果判断是否需要注解，则初始值为false

        for (Class<?> orInterface : orImplementsInterface) {
            boolean isImplementsInterface = ClassHelper.isImplementsInterface(clazz, orInterface);
            if (isImplementsInterface) {
                flag = true;
                break;
            }
        }


        for (Class<?> andInterface : andImplementsInterface) {
            boolean isImplementsInterface = ClassHelper.isImplementsInterface(clazz, andInterface);
            if (!isImplementsInterface) {
                flag = false;
                break;
            }
            flag = true;
        }
        return flag;
    }


    public static void main(String[] args) {
        Collection<Class<?>> scan = PackageScanner.newScanner().addPackage(PackageScanner.class.getPackage()).orAnnotation(Deprecated.class).scan();
        System.out.println(scan);
    }

}
