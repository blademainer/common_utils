package com.xiongyingqi.util;

import java.lang.reflect.Field;
import java.util.Vector;

/**
 * Created by xiongyingqi on 14-3-10.
 */
public class ClassHelper {

    /**
     * 获取加载的所有类
     * @return
     */
    public static Class[] getAllClasses() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> cla = classLoader.getClass();
        while (cla != ClassLoader.class)
            cla = cla.getSuperclass();
        Field field = null;
        try {
            field = cla.getDeclaredField("classes");
            field.setAccessible(true);
            Vector<Class> v = null;
            try {
                v = (Vector<Class>) field.get(classLoader);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < v.size(); i++) {
                System.out.println(v.get(i));
            }
            return v.toArray(new Class[]{});
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查类是否直接或间接实现接口
     *
     * @param clazz
     * @param enterface
     * @return
     */
    public static boolean isImplementsInterface(Class clazz, Class enterface) {
        Assert.notNull(clazz);
        Assert.notNull(enterface);
        Class[] interfaces = clazz.getInterfaces();
        boolean flag = false;
        for (Class anInterface : interfaces) {
            if (enterface == anInterface) {
                return true;
            } else {
                flag = isImplementsInterface(anInterface, enterface);
                if (flag) {
                    return flag;
                }
            }
        }
        return flag;
    }

}
