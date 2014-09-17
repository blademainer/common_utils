package com.xiongyingqi.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/9/17 0017.
 */
public class PrintHelper {
    private Map<String, String> commentMap;
    private String commentPattern = "{0}<{1}>";

    public static PrintHelper needComment() {
        PrintHelper printHelper = new PrintHelper();
        printHelper.commentMap = new HashMap<String, String>();
        return printHelper;
    }

    public PrintHelper addComment(String word, String comment) {
        commentMap.put(word, comment);
        return this;
    }

    private String buildContent(String content) {
        Set<Map.Entry<String, String>> entries = commentMap.entrySet();
        StringBuilder builder = new StringBuilder(content);

        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            replaceWord(builder, key, value);
        }
        return builder.toString();
    }

    private String buildWordAndComment(String word, String comment) {
        String rs = MessageFormat.format(commentPattern, word, comment);
        return rs;
    }

    private void replaceWord(StringBuilder builder, String word, String comment) {
        String replacement = buildWordAndComment(word, comment);
        int fromIndex = 0;
        int index;
        while ((index = builder.indexOf(word, fromIndex)) > 0) {
            fromIndex = index + 1;
            builder.replace(index, index + word.length(), replacement);
        }
    }

    /**
     * 打印对象同时加入注释
     *
     * @param object
     */
    public void printC(Object object) {
        StackTraceElement[] stackTraceElements = StackTraceHelper.getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[2]; // 调用本类的对象类型堆栈
        StringBuilder builder = new StringBuilder();
        builder.append(" ------------------------------------------------------------ ");
        builder.append(StringHelper.line());
        builder.append(StackTraceHelper
                .buildStackTrace(new StackTraceElement[]{stackTraceElement}));
        builder.append("    ");
        if (object == null) {
            builder.append("<null>");
        } else {
            builder.append(object.getClass().getSimpleName());
            builder.append(" =============== ");
            String content = EntityHelper.buildObjectToString(object);
            builder.append(buildContent(content));
        }
        builder.append(StringHelper.line());
        builder.append(" ------------------------------------------------------------ ");
        System.out.println(builder.toString());
    }


    /**
     * 打印对象详情 <br>
     * 2013-8-26 下午2:45:02
     *
     * @param object
     */
    public static void printDetail(Object object) {
        StackTraceElement[] stackTraceElements = StackTraceHelper.getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[2]; // 调用本类的对象类型堆栈
        StringBuilder builder = new StringBuilder();
        builder.append(" ------------------------------------------------------------ ");
        builder.append(StringHelper.line());
        builder.append(StackTraceHelper
                .buildStackTrace(new StackTraceElement[]{stackTraceElement}));
        builder.append("    ");
        if (object == null) {
            builder.append("<null>");
        } else {
            builder.append(object.getClass().getSimpleName());
            builder.append(" =============== ");
            builder.append(EntityHelper.reflectToString(object));
        }
        builder.append(StringHelper.line());
        builder.append(" ------------------------------------------------------------ ");
        System.out.println(builder.toString());
    }

    /**
     * 打印对象， <br>
     * 2013-8-26 下午2:45:02
     *
     * @param object
     */
    public static void print(Object object) {
        StackTraceElement[] stackTraceElements = StackTraceHelper.getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[2]; // 调用本类的对象类型堆栈
        StringBuilder builder = new StringBuilder();
        builder.append(" ------------------------------------------------------------ ");
        builder.append(StringHelper.line());
        builder.append(StackTraceHelper
                .buildStackTrace(new StackTraceElement[]{stackTraceElement}));
        builder.append("    ");
        if (object == null) {
            builder.append("<null>");
        } else {
            builder.append(object.getClass().getSimpleName());
            builder.append(" =============== ");
            builder.append(EntityHelper.buildObjectToString(object));
        }
        builder.append(StringHelper.line());
        builder.append(" ------------------------------------------------------------ ");
        System.out.println(builder.toString());
    }


    public static void print() {
        StackTraceElement[] stackTraceElements = StackTraceHelper.getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[2]; // 调用本类的对象类型堆栈
        StringBuilder builder = new StringBuilder();
        builder.append(" ------------------------------------------------------------ ");
        builder.append(StringHelper.line());
        builder.append(StackTraceHelper
                .buildStackTrace(new StackTraceElement[]{stackTraceElement}));
        builder.append("    ");
        builder.append(StringHelper.line());
        builder.append(" ------------------------------------------------------------ ");
        System.out.println(builder.toString());
    }
}
