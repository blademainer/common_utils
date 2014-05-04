/**
 * YIXUN_1.5_EE
 */
package com.xiongyingqi.jackson;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * 过滤属性处理器
 *
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-9-27 下午6:51:36
 */
public interface FilterPropertyHandler {
    /**
     * 通过传入调用方法和返回值过滤属性 <br>
     * 2013-10-21 上午10:16:27
     *
     * @param method 调用方法
     * @param object 方法返回值
     * @return 过滤属性后的值
     */
    public Object filterProperties(Method method, Object object);
}
