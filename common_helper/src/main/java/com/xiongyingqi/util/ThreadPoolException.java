/**
 * YIXUN_1.5_EE
 */
package com.xiongyingqi.util;

/**
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-9-29 下午3:42:20
 */
public class ThreadPoolException extends Exception {
    /**
     * 2013-9-16 下午10:01:19 long ThreadPool.java
     */
    private static final long serialVersionUID = -5794541119511272494L;

    public ThreadPoolException() {
        super();
    }

    public ThreadPoolException(String message) {
        super(message);
    }

    public ThreadPoolException(Throwable throwable) {
        super(throwable);
    }

    public ThreadPoolException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
