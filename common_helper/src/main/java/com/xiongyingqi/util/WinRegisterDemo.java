/**
 * spark_src
 */
package com.xiongyingqi.util;

/**
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-8-28 下午5:22:25
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class WinRegisterDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(getMyDocumentsFromWinRegistry());
    }

    private static final String REGQUERY_UTIL = "reg query ";
    private static final String REGSTR_TOKEN = "REG_SZ";
    private static final String REGDWORD_TOKEN = "REG_DWORD";

    private static final String PERSONAL_FOLDER_CMD = REGQUERY_UTIL
            + "\"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\"
            + "Explorer\\Shell Folders\" /v Personal";

    /**
     * returns the Path to the "My Documents" folder or <code>null</code>
     *
     * @return {@link String}
     */
    public static String getMyDocumentsFromWinRegistry() {
        try {
            Process process = Runtime.getRuntime().exec(PERSONAL_FOLDER_CMD);
            StreamReader streamreader = new StreamReader(process.getInputStream());

            streamreader.start();
            process.waitFor();
            streamreader.join();

            String result = streamreader.getResult();
            int p = result.indexOf(REGSTR_TOKEN);

            if (p == -1)
                return null;

            return result.substring(p + REGSTR_TOKEN.length()).trim();
        } catch (Exception e) {
            return null;
        }
    }

    static class StreamReader extends Thread {
        private InputStream is;
        private StringWriter sw;

        StreamReader(InputStream is) {
            this.is = is;
            sw = new StringWriter();
        }

        @Override
        public void run() {
            try {
                /**
                 * 读取中文乱码问题
                 */
                /*
				 * int c; while ((c = is.read()) != -1) sw.write(c);
				 */

                /**
                 * 解决中文乱码问题
                 */
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int c = -1;
                while ((c = is.read()) != -1) {
                    bos.write(c);
                }
                sw.write(new String(bos.toString("GBK").getBytes("UTF-8")));
            } catch (IOException e) {
                System.err.println(e);
            }
        }

        String getResult() {
            return sw.toString();
        }
    }
}
