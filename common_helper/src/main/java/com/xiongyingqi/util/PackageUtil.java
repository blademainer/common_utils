package com.xiongyingqi.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackageUtil {
    /**
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // List<String> cls = getClassInPackage("java.util");
        // for (String s : cls) {
        // System.out.println(s);
        // }
//		getclass(PackageUtil.class.getPackage());
//		System.out.println(getclass(PackageUtil.class.getPackage()));

        System.out.println(getRootClassPath());
    }

    public static File getRootClassPath() {
        URL url = PackageUtil.class.getClassLoader().getResource("");
        File file = new File(url.getFile());
        return file;
    }

    private static String[] CLASS_PATH_PROP = {"user.dir", "java.class.path", "java.ext.dirs",
            "sun.boot.class.path"};

    private static List<File> CLASS_PATH_ARRAY = getClassPath();

    private static List<File> getClassPath() {
        List<File> ret = new ArrayList<File>();
        String delim = ":";
        if (System.getProperty("os.name").indexOf("Windows") != -1) {
            delim = ";";
        }
        for (String pro : CLASS_PATH_PROP) {
            try {
                String[] pathes = System.getProperty(pro).split(delim);
                for (String path : pathes) {
                    ret.add(new File(path));
                }
            } catch (Exception e) {
            }
        }
        return ret;
    }

    public static Set<Class<?>> getclass(String pakage) {
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        boolean flag = true;// 是否循环迭代
        String packName = pakage;
        // String packName = "org.jdom";
        String packDir = packName.replace(".", "/");
        Enumeration<URL> dir;
        try {
            dir = Thread.currentThread().getContextClassLoader().getResources(packDir);
            while (dir.hasMoreElements()) {
                URL url = dir.nextElement();
                // System.out.println("url:***" + url);
                String protocol = url.getProtocol();// 获得协议号
                if ("file".equals(protocol)) {
                    // System.err.println("file类型的扫描");
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // System.out.println("filePath :" + filePath);
                    findAndAddClassesInPackageByFile(packName, filePath, flag, classes);
                } else if ("jar".equals(protocol)) {
                    // System.err.println("jar类型扫描");
                    JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                    JarFile jar = urlConnection.getJarFile();
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        // System.out.println(">>>>:" + name);
                        // ......
                    }

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(classes.size());
        return classes;
    }

    public static File getClassDirectory(Package pakage) {
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        boolean flag = true;// 是否循环迭代

        String packName = pakage.getName();
        // String packName = "org.jdom";
        String packDir = packName.replace(".", "/");
        Enumeration<URL> dir;
        String filePath = null;
        try {
            dir = Thread.currentThread().getContextClassLoader().getResources(packDir);
            while (dir.hasMoreElements()) {
                URL url = dir.nextElement();
                // System.out.println("url:***" + url);
                String protocol = url.getProtocol();// 获得协议号
                if ("file".equals(protocol)) {
                    // System.err.println("file类型的扫描");
                    filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(filePath);
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    public static Set<Class<?>> getclass(Package pakage) {
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        boolean flag = true;// 是否循环迭代

        String packName = pakage.getName();
        // String packName = "org.jdom";
        String packDir = packName.replace(".", "/");
        Enumeration<URL> dir;
        try {
            dir = Thread.currentThread().getContextClassLoader().getResources(packDir);
            while (dir.hasMoreElements()) {
                URL url = dir.nextElement();
                // System.out.println("url:***" + url);
                String protocol = url.getProtocol();// 获得协议号
                if ("file".equals(protocol)) {
                    // System.err.println("file类型的扫描");
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // System.out.println("filePath :" + filePath);
                    findAndAddClassesInPackageByFile(packName, filePath, flag, classes);
                } else if ("jar".equals(protocol)) {
                    // System.err.println("jar类型扫描");
                    JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                    JarFile jar = urlConnection.getJarFile();
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        // System.out.println(">>>>:" + name);
                        // ......
                    }

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(classes.size());
        return classes;

    }

    private static void findAndAddClassesInPackageByFile(String packName, String filePath,
                                                         final boolean flag, Set<Class<?>> classes) {
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("此路径下没有文件");
            return;
        }
        File[] dirfiles = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return flag && pathname.isDirectory() || pathname.getName().endsWith(".class");
            }
        });
        for (File file : dirfiles) {
            if (file.isDirectory()) {// 如果是目录，继续扫描
                findAndAddClassesInPackageByFile(packName + "." + file.getName(),
                        file.getAbsolutePath(), flag, classes);
            } else {// 如果是文件
                String className = file.getName().substring(0, file.getName().length() - 6);
                // System.out.println("类名：" + className);
                try {
                    classes.add(Thread.currentThread().getContextClassLoader()
                            .loadClass(packName + "." + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}