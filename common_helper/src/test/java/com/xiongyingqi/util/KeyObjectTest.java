package com.xiongyingqi.util;

public class KeyObjectTest {

    @org.junit.Test
    public void testName() throws Exception {
        KeyObject keyObject = new KeyObject(getClass(), "呵呵");
        EntityHelper.print(keyObject);
    }
}