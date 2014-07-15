package com.xiongyingqi.util;

/**
 * 键对象<br>
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/6/18 0018.
 */
public class KeyObject extends EntityHelper {
    private Class<?> clazz;
    private String[] names;

    public KeyObject(Class<?> clazz, String... name) {
        this.clazz = clazz;
        this.names = name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }
    
}