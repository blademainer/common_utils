/**
 * spark_src
 */
package com.xiongyingqi.utils.thead;

import com.xiongyingqi.util.EntityHelper;
import com.xiongyingqi.util.StackTraceHelper;
import com.xiongyingqi.util.TimerHelper;

import java.lang.Thread.State;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 线程池帮助类<br>
 * 如果要使用本类调用线程，请使用 public static void invoke(Runnable)；<br>
 * 如果要使用代理线程来调用方法则使用 public static void invoke(Object instance, Method method,
 * Object... parameters)； <br>
 * 如果要使用代理线程来调用方法并且需要获取其返回值则使用 public static void invoke(Object
 * callBackInstance, Method callBackMethod, Object instance, Method method,
 * Object... parameters) <br>
 * 初始化时默认使用大小为5的线程池，如果要设置大小，请一定要在使用任何调用方法之前或当线程任务全部执行完之后再执行 public static void
 * setPoolSize(int) 方法<br>
 * <p/>
 * <pre>
 * public static void callBack(Object object, Throwable throwable, Object... parameters) {
 * 	System.out.println(object);
 * 	throwable.printStackTrace();
 * 	for (int i = 0; i &lt; parameters.length; i++) {
 * 		Object parameter = parameters[i];
 * 		System.out.println(&quot;parameter ======== &quot; + parameter);
 *    }
 * }
 *
 * public static void main(String[] args) {
 * 	try {
 * 		ThreadPool.invoke(null, ThreadPool.class.getDeclaredMethod(&quot;callBack&quot;, Object.class,
 * 				Throwable.class, Object[].class), null, Integer.class.getDeclaredMethod(&quot;parseInt&quot;,
 * 				String.class), &quot;ss&quot;);
 *    } catch (SecurityException e) {
 * 		e.printStackTrace();
 *    } catch (NoSuchMethodException e) {
 * 		e.printStackTrace();
 *    }
 * }
 * </pre>
 *
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-9-10 下午4:12:07
 */
public class ThreadPool {
    private static ExecutorService pool = new ScheduledThreadPoolExecutor(5);

    private static PoolStatus status = PoolStatus.STOPED;

    /**
     * PoolStatus
     *
     * @return the status
     */
    public static PoolStatus getStatus() {
        return status;
    }

    /**
     * PoolStatus
     *
     * @param status the status to set
     */
    public static void setStatus(PoolStatus status) {
        ThreadPool.status = status;
    }

    static enum PoolStatus {
        STARTING, STARTED, STOPING, STOPED, WAITING
    }

    public static void setPoolSize(int poolSize) throws ThreadPoolException {
        if (poolSize < 0) {
            throw new IllegalArgumentException("参数个数不能小于0！");
        } else if (status != PoolStatus.STOPED) {
            throw new ThreadPoolException("线程池已经开始运行，不能重新设置大小");
        } else {
            pool = new ScheduledThreadPoolExecutor(poolSize);
        }
    }

    public static void invoke(Runnable runnable) {
        try {
            pool.execute(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 带回调函数的代理线程，本方法将创建新线程在线程池内运行<br>
     * <b>注意：回调函数一定要是带三个参数的方法：<br>
     * 第一个参数来接收代理函数执行方法的返回值，<br>
     * 第二个参数用来接收代理函数执行过程中抛出的异常，<br>
     * 第三个参数用来回调调用时传给代理线程的参数列表</b> <br>
     * <br>
     * <br>
     * 回调方法的获取方法<br>
     * <p/>
     * <pre>
     * Class.getDeclaredMethod(&quot;callBack&quot;, Object.class, Throwable.class, Object[].class);
     * </pre>
     * <p/>
     * 2013-9-16 下午9:30:29
     *
     * @param callBackInstance 接收代理线程执行完成之后调用方法所在对象，如果为静态方法则该参数可为null
     * @param callBackMethod   代理线程执行完后的回调函数，必须是method(Object, Throwable, Object...)形式的函数
     * @param instance         调用实例，如果为静态方法则该参数可为null
     * @param method           调用方法
     * @param parameters       参数列表
     */
    public static void invoke(Object callBackInstance, Method callBackMethod, Object instance,
                              Method method, Object... parameters) {
        if (method != null) {
            ProxyThread proxyThread = new ProxyThread(callBackInstance, callBackMethod, instance,
                    method, parameters);
            pool.execute(proxyThread);
        } else {
            EntityHelper.print("空对象");
            StackTraceHelper.printStackTrace();
        }
    }

    /**
     * 代理线程，本方法将创建新线程在线程池内运行 <br>
     * 2013-9-16 下午9:38:12
     *
     * @param instance   调用实例，如果为静态方法则该参数可为null
     * @param method     调用方法
     * @param parameters 参数列表，必须是Method对应的
     */
    public static void invoke(Object instance, Method method, Object... parameters) {
        invoke(null, null, instance, method, parameters);
    }

    public static void shutDown() {
        status = PoolStatus.STOPING;
        new Thread(new StateThread()).start();
    }

    private static void shutDowned() {
        status = PoolStatus.STOPED;
    }

    static class ProxyThread implements Runnable {
        private Object instance;
        private Method method;
        private Object[] parameters;
        private Method callBackMethod;
        private Object callBackInstance;

        public ProxyThread() {

        }

        public ProxyThread(Object callBackInstance, Method callBackMethod, Object instance,
                           Method method, Object... parameters) {
            this.instance = instance;
            this.method = method;
            this.parameters = parameters;
            this.callBackMethod = callBackMethod;
            this.callBackInstance = callBackInstance;
            // EntityHelper.printDetail(instance);
            // EntityHelper.printDetail(method);
            // EntityHelper.printDetail(parameters);
        }

        /**
         * <br>
         * 2013-9-10 下午4:38:38
         *
         * @see Runnable#run()
         */
        @Override
        public void run() {
            try {
                Object object = method.invoke(instance, parameters);
                if (callBackMethod != null) {
                    try {
                        callBackMethod.invoke(callBackInstance, object, null, parameters);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        //						e.printStackTrace();
                        Throwable t = e.getTargetException();
                        t.printStackTrace();

                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                Throwable t = e.getTargetException();
                try {
                    callBackMethod.invoke(callBackInstance, null, t, parameters);
                } catch (IllegalArgumentException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }
        }

        /**
         * Method
         *
         * @return the callBackMethod
         */
        public Method getCallBackMethod() {
            return this.callBackMethod;
        }

        /**
         * Method
         *
         * @param callBackMethod the callBackMethod to set
         */
        public void setCallBackMethod(Method callBackMethod) {
            this.callBackMethod = callBackMethod;
        }

        /**
         * Object
         *
         * @return the callBackInstance
         */
        public Object getCallBackInstance() {
            return this.callBackInstance;
        }

        /**
         * Object
         *
         * @param callBackInstance the callBackInstance to set
         */
        public void setCallBackInstance(Object callBackInstance) {
            this.callBackInstance = callBackInstance;
        }

        /**
         * Object
         *
         * @return the instance
         */
        public Object getInstance() {
            return instance;
        }

        /**
         * Object
         *
         * @param instance the instance to set
         */
        public void setInstance(Object instance) {
            this.instance = instance;
        }

        /**
         * Method
         *
         * @return the method
         */
        public Method getMethod() {
            return method;
        }

        /**
         * Method
         *
         * @param method the method to set
         */
        public void setMethod(Method method) {
            this.method = method;
        }

        /**
         * Object[]
         *
         * @return the parameters
         */
        public Object[] getParameters() {
            return parameters;
        }

        /**
         * Object[]
         *
         * @param parameters the parameters to set
         */
        public void setParameters(Object[] parameters) {
            this.parameters = parameters;
        }

    }

    static class StateThread implements Runnable {
        @Override
        public void run() {
            ExecutorService executorService = ThreadPool.pool;
            while (true) {
                Thread[] leastThreads = new Thread[Thread.activeCount()];
                int length = Thread.enumerate(leastThreads);

                boolean isAllWaiting = false;
                for (int i = 0; i < leastThreads.length; i++) {
                    Thread thread = leastThreads[i];
                    System.out.println("thread: " + thread.toString());
                    System.out.println(thread.getState());

                    System.out.println("thread.getState() == State.WAITING: "
                            + (thread.getState() == State.WAITING));

                    if (thread.getName().startsWith("pool")) {
                        if (thread.getState() == State.WAITING) {
                            isAllWaiting = true;
                            // thread.interrupt();
                            // System.out.println("等待线程结束：" + thread);
                        } else {
                            isAllWaiting = false;
                        }
                    }
                    // System.out.println(Thread.currentThread().getId());
                }
                if (isAllWaiting) {
                    executorService.shutdownNow();
                    if (executorService.isShutdown()) {
                        System.out.println("pool.isShutdown() ================= "
                                + executorService.isShutdown());
                        shutDowned();
                        return;
                    }
                }
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void callBack(Object object, Throwable throwable, Object... parameters) {
        System.out.println(object);
        throwable.printStackTrace();
        for (int i = 0; i < parameters.length; i++) {
            Object parameter = parameters[i];
            System.out.println("parameter ======== " + parameter);
        }
        System.out.println(TimerHelper.getTime(1));
    }

    public static void main(String[] args) {
        try {
            TimerHelper.getTime(1);
            Thread.sleep(1000);
            ThreadPool.invoke(null, ThreadPool.class.getDeclaredMethod("callBack", Object.class,
                    Throwable.class, Object[].class), null, Integer.class.getDeclaredMethod(
                    "parseInt", String.class), "ss");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
