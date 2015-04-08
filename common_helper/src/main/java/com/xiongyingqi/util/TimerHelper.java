/**
 * YIXUN_2.0
 */
package com.xiongyingqi.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 计时器
 *
 * @author 瑛琪
 * @version 2013-7-26 下午6:05:52
 */
public class TimerHelper {
    static class TimerHolder extends EntityHelper {
        /**
         * 调用计时器的对象
         */
        Object object;
        /**
         * 调用计时器的线程
         */
        Thread thread;

        /**
         * Object
         *
         * @return the object
         */
        public Object getObject() {
            return this.object;
        }

        /**
         * Object
         *
         * @param object the object to set
         */
        public void setObject(Object object) {
            this.object = object;
        }

        /**
         * Thread
         *
         * @return the thread
         */
        public Thread getThread() {
            return this.thread;
        }

        /**
         * Thread
         *
         * @param thread the thread to set
         */
        public void setThread(Thread thread) {
            this.thread = thread;
        }

    }

    private static Map<TimerHolder, Long> holderObjects = new HashMap<TimerHolder, Long>();
    private static Map<Integer, Long> idTimeMap = new HashMap<Integer, Long>();

    /**
     * 获取与上个计时点以毫秒为单位的时差 ，如果是第一个计时点，则开始即时并返回0<br>
     * 此方法是以线程和调用类来区别计时的，即：不同的类或不同的线程都会导致计时器的从0开始计时<br>
     * 2013-7-26 下午6:21:40
     *
     * @return long
     */
    public static long getTime() {
        // StackTraceHelper.printStackTrace();
        StackTraceElement[] stackTraceElements = StackTraceHelper.getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[2]; // 调用本类的对象类型堆栈
        String clazz = stackTraceElement.getClassName(); // 调用本类的对象类型
        Thread thread = Thread.currentThread();
        TimerHolder holder = new TimerHolder();
        holder.setObject(clazz);
        holder.setThread(thread);
        // String methodName = stackTraceElement.getMethodName();
        // int lineNumber = stackTraceElement.getLineNumber();
        // long hashCode = 17;
        // hashCode = 37 * hashCode + clazz.hashCode();
        // hashCode = 37 * hashCode + methodName.hashCode();
        // hashCode = 37 * hashCode + lineNumber;
        // System.out.println("stackTraceElement.getClassName() ========= " +
        // stackTraceElement.getClassName());
        Long startTime = holderObjects.get(holder);
        // System.out.println(stackTraceElement);
        if (startTime == null) {
            startTime = System.currentTimeMillis();
        }
        long time = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        holderObjects.put(holder, startTime);
        return time;
    }

    public static long getTime(int id) {
        // StackTraceHelper.printStackTrace();
        StackTraceElement[] stackTraceElements = StackTraceHelper.getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[2]; // 调用本类的对象类型堆栈
        String clazz = stackTraceElement.getClassName(); // 调用本类的对象类型
        // String methodName = stackTraceElement.getMethodName();
        // int lineNumber = stackTraceElement.getLineNumber();
        // long hashCode = 17;
        // hashCode = 37 * hashCode + clazz.hashCode();
        // hashCode = 37 * hashCode + methodName.hashCode();
        // hashCode = 37 * hashCode + lineNumber;
        // System.out.println("stackTraceElement.getClassName() ========= " +
        // stackTraceElement.getClassName());
        Long startTime = idTimeMap.get(id);
        // System.out.println(stackTraceElement);
        if (startTime == null) {
            startTime = System.currentTimeMillis();
        }
        long time = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        idTimeMap.put(id, startTime);
        return time;
    }

    public static void main(String[] args) {
        System.out.println(getTime());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getTime());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getTime());
    }
}
