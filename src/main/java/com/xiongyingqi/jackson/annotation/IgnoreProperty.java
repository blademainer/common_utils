/**
 * YIXUN_1.5_EE
 */
package com.xiongyingqi.jackson.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于注解json过滤pojo内的属性，其他的属性都会被序列化成字符串
 *
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-9-27 下午4:24:33
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreProperty {
    /**
     * 要忽略字段的POJO <br>
     * 2013-9-27 下午4:27:08
     *
     * @return
     */
    Class<?> pojo();

    /**
     * 要忽略的字段名 <br>
     * 2013-9-27 下午4:27:12
     *
     * @return
     */
    String[] name();

    /**
     * 字段名，无论是哪种 <br>
     * 2013-9-27 下午4:27:15
     *
     * @return
     */
    //	String value() default "";

    /**
     * 最大迭代层次<br>
     * 当注解了pojo和name值时，该值表示遍历bean属性的最大曾次数，此注解一般用于自关联的bean类，
     * 如果循环层次大于等于maxLevel时则不再读取属性<br>
     * 如果maxIterationLevel为0，则不限制迭代层次<br>
     * 如果maxIterationLevel为1，则迭代读取属性一次<br>
     * 2013-10-21 下午2:16:26
     *
     * @return
     */
    //	int maxIterationLevel() default 0;
}
