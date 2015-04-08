package com.xiongyingqi.util;

/**
 * 堆栈帮助类
 *
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-8-28 下午3:51:58
 */
public class StackTraceHelper {
    /**
     * 打印出调用堆栈 <br>
     * 2013-8-28 下午3:52:17
     */
    public static void printStackTrace() {
        Throwable throwable = new Throwable();
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        StackTraceElement[] stackTraceElementsTarget = new StackTraceElement[stackTraceElements.length - 1];
        // for (int i = 1, j = 0; i < stackTraceElements.length; i++) {
        // StackTraceElement stackTraceElement = stackTraceElements[i];
        // System.out.println(stackTraceElement.getClassName());
        // System.out.println(stackTraceElement.getFileName());
        // System.out.println(stackTraceElement.getMethodName());
        // System.out.println(stackTraceElement.getLineNumber());
        // System.out.println("    at " + stackTraceElement.getClassName() + "."
        // + stackTraceElement.getMethodName() + "(" +
        // stackTraceElement.getFileName() + ":" +
        // stackTraceElement.getLineNumber() + ")");
        // }
        System.arraycopy(stackTraceElements, 1, stackTraceElementsTarget, 0,
                stackTraceElementsTarget.length);
        System.out.println(" ----------------------- StackTrace Info ----------------------- ");
        System.out.print(buildStackTrace(stackTraceElementsTarget));
    }

    /**
     * 组织堆栈信息，将堆栈数组拼接成字符串 <br>
     * 2013-8-28 下午3:50:50
     *
     * @param stackTraceElements
     * @return String
     */
    public static String buildStackTrace(StackTraceElement[] stackTraceElements) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < stackTraceElements.length; i++) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
            builder.append("    at ");
            builder.append(stackTraceElement.getClassName());
            builder.append(".");
            builder.append(stackTraceElement.getMethodName());
            builder.append("(");
            builder.append(stackTraceElement.getFileName());
            builder.append(":");
            builder.append(stackTraceElement.getLineNumber());
            builder.append(")");
            builder.append(StringHelper.line());
        }
        return builder.toString();
    }

    /**
     * 返回不包括本类调用堆栈的当前堆栈 <br>
     * 2013-7-26 下午6:09:09
     *
     * @return
     */
    public static StackTraceElement[] getStackTrace() {
        Throwable throwable = new Throwable();
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        StackTraceElement[] stackTraceElementsTarget = new StackTraceElement[stackTraceElements.length];
        System.arraycopy(stackTraceElements, 0, stackTraceElementsTarget, 0,
                stackTraceElementsTarget.length);
        return stackTraceElementsTarget;
    }


    /**
     * 获取间接调用的类名<p></p>
     * 比如A方法调用B方法，B方法再调用whoInvoke()方法，这样B方法内就能返回A方法在那个类下
     *
     * @return
     */
    public static Class whoInvoke() {
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

    public static void main(String[] args) {
        printStackTrace();
        StackTraceElement[] stackTraceElements = getStackTrace();
        for (int i = 1; i < stackTraceElements.length; i++) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
            // System.out.println(stackTraceElement.getClassName());
            // System.out.println(stackTraceElement.getFileName());
            // System.out.println(stackTraceElement.getMethodName());
            // System.out.println(stackTraceElement.getLineNumber());
            System.out.println("    at " + stackTraceElement.getClassName() + "."
                    + stackTraceElement.getMethodName() + "(" + stackTraceElement.getFileName()
                    + ":" + stackTraceElement.getLineNumber() + ")");
        }
        // Long.parseLong("s");
    }
}
