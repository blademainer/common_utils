/**
 * WebSocket
 */
package com.xiongyingqi.util;

/**
 * @author 瑛琪
 * @version 2013-8-7 下午3:30:03
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Test {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        try {

            MyClassLoader loader = new MyClassLoader();

            File folder = new File("builder/classes");

            if (folder.isDirectory()) {

                File[] fs = folder.listFiles();

                byte[] bs = null;

                InputStream in = null;

                int length = 0;

                Class cls = null;

                for (File f : fs) {

                    in = new FileInputStream(f);

                    bs = new byte[in.available()];

                    length = in.read(bs);

                    cls = loader.loadClass(null, bs, length);

                    System.out.println(cls.newInstance());
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
