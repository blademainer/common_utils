package com.xiongyingqi.util;

/**
 * 反射帮助类
 * Created by qi on 14-3-7.
 */
public class ReflectHelper {
    /**
     * 判断class是否实现了接口类
     *
     * @param clazz          要判断的类
     * @param interfaceClass 接口类
     * @return boolean
     */
    public static boolean isContainsInterface(Class<?> clazz, Class<?> interfaceClass) {
        Class<?>[] interfaces = clazz.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            Class<?> class1 = interfaces[i];
            if (class1 == interfaceClass) {
                return true;
            }
        }
        return false;
    }
}
