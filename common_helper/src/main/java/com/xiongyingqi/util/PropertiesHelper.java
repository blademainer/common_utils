package com.xiongyingqi.util;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 属性帮助类
 * Created by xiongyingqi on 14-3-7.
 */
public class PropertiesHelper {

    /**
     * 读取属性文件
     *
     * @param propertiesFilePath
     * @return Map<String, String>
     */
    public static Map<String, String> readProperties(String propertiesFilePath) {
        return readProperties(new File(propertiesFilePath));
    }

    /**
     * 读取属性文件
     *
     * @param propertiesFile
     * @return Map<String, String>
     */
    public static Map<String, String> readProperties(File propertiesFile) {
        if (!propertiesFile.exists()) {
            return null;
        }
        Map<String, String> propertiesMap = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(propertiesFile);
            propertiesMap = new HashMap<String, String>();

            Properties properties = new Properties();
            properties.load(inputStream);

            Enumeration<?> enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                String value = (String) properties.get(key);
                propertiesMap.put(key, value);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return propertiesMap;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("test.properties");
        readProperties(file);
//        File xmlFile = new File("test.xml");
//	    EntityHelper.print(file.getCanonicalPath());
//        System.out.println(file.exists());
//        FileOutputStream fileOutputStream = new FileOutputStream(file);
//        FileOutputStream fileOutputStreamXml = new FileOutputStream(xmlFile);
//        Properties properties = new Properties();
//        properties.put("test", "test");
//        properties.put("test2", "test");
//        properties.put("test3", "test");
//        properties.store(fileOutputStream, "属性测试");
//        properties.storeToXML(fileOutputStreamXml, "属性测试");
//        fileOutputStream.flush();
//        fileOutputStream.close();
//        fileOutputStreamXml.flush();
//        fileOutputStreamXml.close();
    }
}
