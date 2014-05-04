/**
 * YIXUN_1.5_EE
 */
package com.xiongyingqi.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密帮助类
 *
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-11-4 下午6:06:54
 */
public class MD5Helper {
    /**
     * MD5 加密， 内容默认使用UTF-8编码，结果为小写 <br>
     * 2013-11-4 下午6:33:06
     *
     * @param message 要加密的内容
     * @return 加密后的信息
     */
    public static String encrypt(String message) {
        return encrypt(message, "UTF-8", false);
    }

    /**
     * MD5 加密， 结果默认为小写 <br>
     * 2013-11-4 下午6:33:06
     *
     * @param message 要加密的内容
     * @param encode  message的编码方式
     * @return 加密后的信息
     */
    public static String encrypt(String message, String encode) {
        return encrypt(message, encode, false);
    }

    /**
     * MD5 加密， 内容默认使用UTF-8编码 <br>
     * 2013-11-4 下午6:33:06
     *
     * @param message     要加密的内容
     * @param toUpperCase 是否转换成大写
     * @return 加密后的信息
     */
    public static String encrypt(String message, boolean toUpperCase) {
        return encrypt(message, "UTF-8", toUpperCase);
    }

    /**
     * MD5 加密 <br>
     * 2013-11-4 下午6:33:06
     *
     * @param message     要加密的内容
     * @param encode      message的编码方式
     * @param toUpperCase 是否转换成大写
     * @return 加密后的信息
     */
    public static String encrypt(String message, String encode, boolean toUpperCase) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(message.getBytes(encode));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                String hex = Integer.toHexString(0xFF & byteArray[i]);
                if (toUpperCase) {
                    hex = hex.toUpperCase();
                }
                md5StrBuff.append(hex);
            }
        }
        return md5StrBuff.toString();
    }

    public static void main(String[] args) {
        System.out.println(encrypt("", true));
        System.out.println(encrypt("value"));
    }

}
