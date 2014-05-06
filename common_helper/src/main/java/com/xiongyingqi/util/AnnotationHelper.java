/**
 * WebSocketForJavaEE7
 */
package com.xiongyingqi.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author xiongyingqi <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2014年2月25日 下午12:05:17
 */
public class AnnotationHelper {
    public static <T extends Annotation> boolean isAnnotationType(Class<?> clazz,
                                                                  Class<T> annotationClass) {
        return clazz.isAnnotationPresent(annotationClass);
    }

    public static <T extends Annotation> T readAnnotationValue(Class<?> clazz,
                                                               Class<T> annotationClass) {
        if (isAnnotationType(clazz, annotationClass)) {
            T t = clazz.getAnnotation(annotationClass);
            return t;
        } else {
            return null;
        }
    }

    public static <T extends Annotation> T readAnnotationValueOnField(Field field, Class<T> annotationClass) {
        if (field.isAnnotationPresent(annotationClass)) {
            T t = field.getAnnotation(annotationClass);
            return t;
        } else {
            Method method = EntityHelper.findGetMethod(field);
            if (method.isAnnotationPresent(annotationClass)) {
                T t = method.getAnnotation(annotationClass);
                return t;
            }
            return null;
        }
    }

    public static Collection<? super Annotation> readAnnotationsOnField(Field field) {
        Collection<? super Annotation> annotations = new HashSet<>();

        Annotation[] annotationArray = field.getAnnotations();
        if (annotationArray != null && annotationArray.length > 0) {
            Collections.addAll(annotations, annotationArray);
            return annotations;
        }

        Method method = EntityHelper.findGetMethod(field);
        Annotation[] methodAnnotations = method.getAnnotations();
        if (methodAnnotations != null && methodAnnotations.length > 0) {
            Collections.addAll(annotations, methodAnnotations);
            return annotations;
        }

        return null;
    }

    /**
     * 在类内搜索所有的字段的annotationClass注解值
     * @param clazz
     * @param annotationClass
     * @param <T>
     * @return
     */
    public static <T extends Annotation> Collection<T> readAnnotationsInClass(Class<?> clazz, Class<T> annotationClass) {
        Collection<T> collection = null;

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            T t = readAnnotationValueOnField(field, annotationClass);
            if(t == null){
                continue;
            }

            if(collection == null){
                collection = new ArrayList<T>();
            }

            collection.add(t);
        }

        return collection;
    }

}
