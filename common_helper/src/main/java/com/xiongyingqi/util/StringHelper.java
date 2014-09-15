/**
 * openfire_src
 */
package com.xiongyingqi.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * @author XiongYingqi
 * @version 2013-6-17 上午11:19:19
 */
public class StringHelper {

    /**
     * 从第一个字符开始比较两个字符串的大小（按照单个字符的ascii码比较），例如： "abc"大于"aac"、"1234"小于"2234"、"1234"大于"123"、"223"大于"1234"
     *
     * @param firstString  第一个字符串
     * @param secondString 第二个字符串
     * @return 如果第一个字符串小于第二个字符串，则返回true，否则返回false
     */
    public static boolean compareNumberString(String firstString, String secondString) {
        Assert.notNull(firstString, "第一个字符串为空！");
        Assert.notNull(secondString, "第二个字符串为空！");
        char[] chars1 = firstString.toCharArray();
        char[] chars2 = secondString.toCharArray();
        int length1 = chars1.length;
        int length2 = chars2.length;
        int maxLength = length1 > length2 ? length1 : length2;
        for (int i = 0; i < maxLength; i++) {
            int value1 = -1;
            int value2 = -1;
            if (i < length1) {
                value1 = chars1[i];
            }

            if (i < length2) {
                value2 = chars2[i];
            }

            if(value1 < value2){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

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
        //"abc"大于"aac"、"1234"小于"2234"、"1234"大于"123"、"223"大于"1234"
        System.out.println(compareNumberString("", ""));
        System.out.println(compareNumberString("abc", "aac"));
        System.out.println(compareNumberString("1234", "2234"));
        System.out.println(compareNumberString("1234", "123"));
        System.out.println(compareNumberString("223", "1234"));
    }
}
