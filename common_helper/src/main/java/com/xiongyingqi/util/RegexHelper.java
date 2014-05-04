/**
 * spark_src
 */
package com.xiongyingqi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则帮助类
 *
 * @author XiongYingqi
 * @version 2013-7-3 下午3:35:18
 */
public class RegexHelper {

    public static String replaceAllHTMLComment(String content) {
        Pattern pattern = Pattern.compile("<\\w+.*?>.*?</\\w+.*?>");
        Matcher matcher = pattern.matcher(content);
        String rs = matcher.replaceAll("");
        return rs;
    }

}
