package com.xiongyingqi.util;


import java.util.ArrayList;
import java.util.Collection;

public class ClassHelperTest {


    @org.junit.Test
    public void testIsImplementsInterface() throws Exception {
        org.junit.Assert.assertTrue(ClassHelper.isImplementsInterface(ArrayList.class, Collection.class));
        org.junit.Assert.assertFalse(ClassHelper.isImplementsInterface(String.class, Collection.class));
    }
}