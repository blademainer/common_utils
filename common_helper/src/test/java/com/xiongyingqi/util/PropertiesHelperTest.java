package com.xiongyingqi.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Map;

public class PropertiesHelperTest {
    private File propertiesFile;

    @Before
    public void setUp() throws Exception {
        String filePath = getClass().getClassLoader().getResource("").getFile();
        File file = new File(filePath);
        propertiesFile = new File(file, "test.properties");
        System.out.println(propertiesFile);
        if (!propertiesFile.exists()) {
            propertiesFile.createNewFile();
        }
    }

    @After
    public void tearDown() throws Exception {
        propertiesFile.delete();
    }

    @org.junit.Test
    public void testReadProperties() throws Exception {
        Map<String, String> map = PropertiesHelper.readProperties(propertiesFile);
        Assert.notNull(map);
    }

    @Test
    public void testSave() throws Exception {
        Map<String, String> map = PropertiesHelper.readProperties(propertiesFile);
        Assert.notNull(map);
        map.put("aaa", "test");
        PropertiesHelper.save(map, propertiesFile);

        Map<String, String> map2 = PropertiesHelper.readProperties(propertiesFile);
        String aaa = map2.get("aaa");
        Assert.hasText(aaa);
    }

}