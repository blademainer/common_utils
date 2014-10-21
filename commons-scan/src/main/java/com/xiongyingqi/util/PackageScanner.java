package com.xiongyingqi.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/10/21 0021.
 */
public class PackageScanner {
    private Collection<Package> packages;
    private Collection<Class<? extends Annotation>> withAnnotations;

    private PackageScanner() {
    }

    public static PackageScanner newScanner() {
        PackageScanner packageScanner = new PackageScanner();
        packageScanner.packages = new ArrayList<Package>();
        packageScanner.withAnnotations = new ArrayList<Class<? extends Annotation>>();
        return packageScanner;
    }

    public void addPackage(String pkg) {

    }

    public void addPackage(Package pkg) {

    }

}
