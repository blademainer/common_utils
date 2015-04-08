/**
 * WebSocket
 */
package com.xiongyingqi.util;

/**
 * @author 瑛琪
 * @version 2013-8-7 下午3:30:24
 */
public class MyClassLoader extends ClassLoader {
    @SuppressWarnings("unchecked")
    public Class loadClass(String name, byte[] bs, int length) {
        return super.defineClass(name, bs, 0, length);
    }
}
