package com.xiongyingqi.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

@PackageScannerTest.PackageScannerTestAnnotation
public class PackageScannerTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    @interface PackageScannerTestAnnotation {
    }

    @org.junit.Test
    public void testScan() throws Exception {
        Collection<Class<?>> scan = PackageScanner.newScanner()
                .addPackage(PackageScanner.class.getPackage())
                .scan();
        org.junit.Assert.assertTrue(scan.size() > 0);
    }


    @org.junit.Test
    public void testWithAnnotation() throws Exception {
        Collection<Class<?>> scan = PackageScanner.newScanner()
                .addPackage(PackageScanner.class.getPackage())
                .orAnnotation(PackageScannerTestAnnotation.class)
                .scan();
        org.junit.Assert.assertEquals(scan.iterator().next(), getClass());
    }


    @org.junit.Test
    public void testStartWithString() throws Exception {
        Collection<Class<?>> scan = PackageScanner.newScanner()
                .addPackage(PackageScanner.class.getPackage())
                .orStartWithString("PackageScannerTest")
                .scan();
        org.junit.Assert.assertTrue(scan.contains(getClass()));
        System.out.println(scan);
    }

    @org.junit.Test
    public void testEndWithString() throws Exception {
        Collection<Class<?>> scan = PackageScanner.newScanner()
                .addPackage(PackageScanner.class.getPackage())
                .orEndWithString("ScannerTest")
                .scan();
        org.junit.Assert.assertTrue(scan.contains(getClass()));
        System.out.println(scan);
    }
}