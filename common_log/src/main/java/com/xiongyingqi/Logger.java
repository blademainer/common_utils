package com.xiongyingqi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/5/6 0006.
 */
public class Logger {
    private static Map<String, org.apache.log4j.Logger> nameLoggerMap = new HashMap<String, org.apache.log4j.Logger>();

    // ---------------------------- debug　----------------------------
    public static void debug(Object object, Object message) {
        Class clazz = object.getClass();
        debug(clazz, message);
    }

    public static void debug(Class clazz, Object message) {
        org.apache.log4j.Logger logger = getLogger(clazz);
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    public static void debug(Object object, Object message, Throwable cause) {
        Class clazz = object.getClass();
        debug(clazz, message, cause);
    }

    public static void debug(Class clazz, Object message, Throwable cause) {
        org.apache.log4j.Logger logger = getLogger(clazz);
        if (logger.isDebugEnabled()) {
            logger.debug(message, cause);
        }
    }

    public static void debug(String name, Object message) {
        org.apache.log4j.Logger logger = getLogger(name);
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    public static void debug(Object message) {
        debug(whoInvoke(), message);
    }

    public static void debug(String name, Object message, Throwable cause) {
        org.apache.log4j.Logger logger = getLogger(name);
        if (logger.isDebugEnabled()) {
            logger.debug(message, cause);
        }
    }


    // ---------------------------- info　----------------------------

    public static void info(Object object, Object message) {
        info(object.getClass(), message);
    }

    public static void info(Class clazz, Object message) {
        org.apache.log4j.Logger logger = getLogger(clazz);
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }


    public static void info(Object object, Object message, Throwable cause) {
        Class clazz = object.getClass();
        info(clazz, message, cause);
    }

    public static void info(Class clazz, Object message, Throwable cause) {
        org.apache.log4j.Logger logger = getLogger(clazz);
        if (logger.isInfoEnabled()) {
            logger.info(message, cause);
        }
    }

    public static void info(String name, Object message) {
        org.apache.log4j.Logger logger = getLogger(name);
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    public static void info(String name, Object message, Throwable cause) {
        org.apache.log4j.Logger logger = getLogger(name);
        if (logger.isInfoEnabled()) {
            logger.info(message, cause);
        }
    }

    public static void info(Object message) {
        info(whoInvoke(), message);
    }

    // ---------------------------- warn　----------------------------
    public static void warn(Object object, Object message) {
        warn(object.getClass(), message);
    }

    public static void warn(Class clazz, Object message) {
        org.apache.log4j.Logger logger = getLogger(clazz);
        logger.warn(message);
    }

    public static void warn(Object object, Object message, Throwable cause) {
        warn(object.getClass(), message, cause);
    }

    public static void warn(Class clazz, Object message, Throwable cause) {
        org.apache.log4j.Logger logger = getLogger(clazz);
        logger.warn(message, cause);
    }

    public static void warn(String name, Object message) {
        org.apache.log4j.Logger logger = getLogger(name);
        logger.warn(message);
    }

    public static void warn(String name, Object message, Throwable cause) {
        org.apache.log4j.Logger logger = getLogger(name);
        logger.warn(message, cause);
    }

    public static void warn(Object message) {
        warn(whoInvoke(), message);
    }

    // ---------------------------- error　----------------------------
    public static void error(Object object, Object message) {
        error(object.getClass(), message);
    }

    public static void error(Class clazz, Object message) {
        org.apache.log4j.Logger logger = getLogger(clazz);
        logger.error(message);
    }

    public static void error(Object object, Object message, Throwable cause) {
        error(object.getClass(), message, cause);
    }

    public static void error(Class clazz, Object message, Throwable cause) {
        org.apache.log4j.Logger logger = getLogger(clazz);
        logger.warn(message, cause);
    }

    public static void error(String name, Object message) {
        org.apache.log4j.Logger logger = getLogger(name);
        logger.warn(message);
    }

    public static void error(String name, Object message, Throwable cause) {
        org.apache.log4j.Logger logger = getLogger(name);
        logger.warn(message, cause);
    }

    public static void error(Object message) {
        error(whoInvoke(), message);
    }

    /**
     * 获取间接调用的类名<p></p>
     * 比如A方法调用B方法，B方法再调用whoInvoke()方法，这样B方法内就能返回A方法在那个类下
     *
     * @return
     */
    private static Class whoInvoke() {
        Throwable throwable = new Throwable();
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[2];
        try {
            return Class.forName(stackTraceElement.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static org.apache.log4j.Logger getLogger(Class clazz) {
        return getLogger(clazz.getName());
    }

    private static org.apache.log4j.Logger getLogger(String name) {
        org.apache.log4j.Logger logger = nameLoggerMap.get(name);
        if (logger == null) {
            logger = org.apache.log4j.Logger.getLogger(name);
        }
        return logger;
    }

    public static void main(String[] args) {
        debug("呵呵");
    }

}
