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

}
