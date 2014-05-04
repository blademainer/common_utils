package com.xiongyingqi.utils.code;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 注解帮助类
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/3/31 0031.
 */
public class CodeAnnotation {
    private Map<Class<?>, Class<?>> toAddAnnotationsByExistsAnnotation;
    private Map<Class<?>, Map<String, String>> toAddAnnotationProperties;

    /**
     * 为已经存在的注解新增参数列表
     *
     * @param clazz
     * @param parameters
     * @return
     */
    public CodeAnnotation addAnnotationProperties(Class<?> clazz, Map<String, String> parameters) {
        return this;
    }


    /**
     * 为已经存在的注解方法、类、属性、参数等新增注解
     *
     * @param existsAnnotation 已经存在的Annotation
     * @param newAnnotation    新增的Annotation
     * @return
     */
    public CodeAnnotation addAnnotationByExistsAnnotation(Class<?> existsAnnotation, Class<?> newAnnotation) {

        return this;
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<?> clazz = SuppressWarnings.class;
        System.out.println(clazz.isAnnotation());
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("value")) {
                Object instance = clazz.newInstance();
                try {
                    Object re = method.invoke(instance);
                    System.out.println(re);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(method);
        }


    }
}
