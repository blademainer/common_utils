/**
 * YIXUN_1.5_EE
 */
package com.xiongyingqi.jackson.annotation;

import java.lang.annotation.*;

/**
 * 只允许pojo内的属性序列化成json，对于同一个pojo该注解是与IgnoreProperty是冲突的<br>
 *
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-10-30 下午3:57:35
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowProperty {
    /**
     * 目标POJO <br>
     * 2013-9-27 下午4:27:08
     *
     * @return
     */
    Class<?> pojo();

    /**
     * 允许序列化的属性名 <br>
     * 2013-9-27 下午4:27:12
     *
     * @return
     */
    String[] name();
}
