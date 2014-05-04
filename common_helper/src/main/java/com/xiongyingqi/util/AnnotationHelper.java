/**
 * WebSocketForJavaEE7
 */
package com.xiongyingqi.util;

import java.lang.annotation.Annotation;

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
}
