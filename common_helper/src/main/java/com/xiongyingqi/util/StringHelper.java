/**
 * openfire_src
 */
package com.xiongyingqi.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * @author KRXiongYingqi
 * @version 2013-6-17 上午11:19:19
 */
public class StringHelper {

    public static String encode(String str, String charset) {
        String content = null;
        try {
            content = new String(str.getBytes("ISO-8859-1"), charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 判断字符串不为空，并且字符串内容不为空
     *
     * @param input
     * @return
     */
    public static boolean notNullAndNotEmpty(String input) {
        return input != null && !"".equals(input.trim());
    }

    /**
     * 计算数组的hashCode <br>
     * 2013-10-25 上午11:06:57
     *
     * @param stringArray
     * @return
     */
    public static int hashCodeOfStringArray(String[] stringArray) {
        if (stringArray == null) {
            return 0;
        }
        int hashCode = 17;
        for (int i = 0; i < stringArray.length; i++) {
            String value = stringArray[i];
            hashCode = hashCode * 31 + (value == null ? 0 : value.hashCode());
        }
        return hashCode;
    }

    /**
     * 判断字符串是否为空，或者字符串内容为空
     *
     * @param input
     * @return
     */
    public static boolean nullOrEmpty(String input) {
        return input == null || "".equals(input.trim());
    }

    /**
     * 换行符 <br>
     * 2013-8-14 下午1:20:06
     *
     * @return
     */
    public static String line() {
        String lineSeparator = java.security.AccessController.doPrivileged(
                new sun.security.action.GetPropertyAction("line.separator"));
//		String lineSeparator = System.getProperty("line.separator");
        return lineSeparator;
    }

    /**
     * <br>
     * 2013-8-28 下午5:26:24
     *
     * @param str
     * @return
     */
    public static String convertEncode(String str) {
        if (nullOrEmpty(str)) {
            return null;
        }
        try {
            return new String(str.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * StringReader转换为字符串 <br>
     * 2013-9-2 下午9:03:29
     *
     * @param reader
     * @return
     */
    public String stringReaderToString(StringReader reader) {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[128];
        int length = -1;
        try {
            while ((length = reader.read(buffer)) != -1) {
                if (buffer.length != length) {
                    System.arraycopy(buffer, 0, buffer, 0, length);
                }
                builder.append(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        return builder.toString();
    }

    public static String replaceFirst(String content, String target, String replacement) {
        int index = content.indexOf(target);
        if (index < 0) {
            return content;
        }
        StringBuilder builder = new StringBuilder();
        String front = content.substring(0, index);
        String last = content.substring(index + target.length());
        return builder.append(front).append(replacement).append(last).toString();
    }

    public static String UUID() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String[] args) {
        System.out.println(replaceFirst("asfasf.ccc.bbb.ccc.cccc.", "ccc.", "dddd."));
    }
}
