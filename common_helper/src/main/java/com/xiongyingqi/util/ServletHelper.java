/**
 * YIXUN_1.5_EE
 */
package com.xiongyingqi.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-11-6 下午5:03:28
 */
public class ServletHelper {
    public static void writeJson(HttpServletResponse response, String json) {
        response.setContentType("text/plain;charset=UTF-8");
        //		response.setContentType("application/x-javascript");
        // response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");// 没有缓存，立即返回响应
        PrintWriter out;
        try {
            out = response.getWriter();
            out.print(json);// 不能用println
            // System.out.println("jsonArray ====== " + jsonArray);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
