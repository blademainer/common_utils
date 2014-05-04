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
 * json属性过滤注解，对于同一个pojo来说 @AllowProperty 是与 @IgnoreProperty 是冲突的，如果这两个注解注解了<br>
 * 例如以下代码YxResource实体只会显示resourceName和resourceDescribe属性
 * <p/>
 * <pre>
 * &#064;IgnoreProperties(
 * 	value = {
 * 		&#064;IgnoreProperty(
 * 			pojo = YxResource.class,
 * 			name = {
 * 				"yxResourceDataRelations",
 * 				"yxResourceSelfRelationsForSublevelResourceId",
 * 				"yxPermisionResourceRelations" }),
 * 		&#064;IgnoreProperty(
 * 			pojo = YxResourceSelfRelation.class,
 * 			name = {
 * 				"yxResourceBySuperiorResourceId",
 * 				"id" })
 *    },
 * 	allow = {
 * 	&#064;AllowProperty(
 * 			pojo = YxResource.class,
 * 			name = { "<b><i>resourceName</i></b>" }) })
 * 	&#064;AllowProperty(
 * 			pojo = YxResource.class,
 * 			name = { "<b><i>resourceDescribe</i></b>" })
 * </pre>
 * <p/>
 * <p/>
 * 但是，对于同一个pojo的同一属性来说@AllowProperty是与@IgnoreProperty则会按照@IgnoreProperty过滤的属性名过滤
 * 例如以下代码YxResource实体不会显示resourceName属性的值
 * <p/>
 * <pre>
 * &#064;IgnoreProperties(
 * 	value = {
 * 	&#064;IgnoreProperty(
 * 			pojo = YxResource.class,
 * 			name = { "<b><i>resourceName</i></b>",
 * 				"yxResourceDataRelations",
 * 				"yxResourceSelfRelationsForSublevelResourceId",
 * 				"yxPermisionResourceRelations" }),
 * 	&#064;IgnoreProperty(
 * 			pojo = YxResourceSelfRelation.class,
 * 			name = {
 * 				"yxResourceBySuperiorResourceId",
 * 				"id" })
 *    },
 * 	allow = {
 * 	&#064;AllowProperty(
 * 			pojo = YxResource.class,
 * 			name = { "<b><i>resourceName</i></b>" }) })
 * </pre>
 *
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-9-27 下午4:18:39
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreProperties {
    /**
     * 要过滤的属性
     *
     * @return
     */
    IgnoreProperty[] value() default @IgnoreProperty(pojo = Object.class, name = "");

    /**
     * 允许的属性
     *
     * @return
     */
    AllowProperty[] allow() default @AllowProperty(pojo = Object.class, name = "");
}
