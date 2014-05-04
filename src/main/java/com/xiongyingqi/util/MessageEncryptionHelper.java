package com.xiongyingqi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xiongyingqi on 14-3-10.
 */
public class MessageEncryptionHelper {
    public static final boolean toUpperCase = true;

    /**
     * 将摘要信息转换为相应的编码
     *
     * @param code    编码类型
     * @param message 摘要信息
     * @return 相应的编码字符串
     */
    private static String encode(String code, String message) {
        return encode(code, message.getBytes());
    }

    private static String encode(String code, byte[] message) {
        MessageDigest md;
        String encode = null;
        try {
            md = MessageDigest.getInstance(code);
            encode = byteArrayToHexString(md.digest(message));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encode;
    }

    private static String byteArrayToHexString(byte[] digest) {
        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < digest.length; i++) {
            if (Integer.toHexString(0xFF & digest[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & digest[i]));
            } else {
                String hex = Integer.toHexString(0xFF & digest[i]);
                if (toUpperCase) {
                    hex = hex.toUpperCase();
                }
                md5StrBuff.append(hex);
            }
        }
        return md5StrBuff.toString();
    }

    /**
     * 将摘要信息转换成MD5编码
     *
     * @param message 摘要信息
     * @return MD5编码之后的字符串
     */
    public static String md5Encode(String message) {
        return encode("MD5", message);
    }

    /**
     * 将摘要信息转换成SHA编码
     *
     * @param message 摘要信息
     * @return SHA编码之后的字符串
     */
    public static String shaEncode(String message) {
        return encode("SHA", message);
    }

    /**
     * 将摘要信息转换成SHA-256编码
     *
     * @param message 摘要信息
     * @return SHA-256编码之后的字符串
     */
    public static String sha256Encode(String message) {
        return encode("SHA-256", message);
    }

    /**
     * 将摘要信息转换成SHA-512编码
     *
     * @param message 摘要信息
     * @return SHA-512编码之后的字符串
     */
    public static String sha512Encode(String message) {
        return encode("SHA-512", message);
    }

    /**
     * 将摘要信息转换成SHA编码
     *
     * @param message 摘要信息
     * @return SHA编码之后的字符串
     */
    public static String shaEncode(byte[] message) {
        return encode("SHA", message);
    }

    /**
     * 将摘要信息转换成SHA-256编码
     *
     * @param message 摘要信息
     * @return SHA-256编码之后的字符串
     */
    public static String sha256Encode(byte[] message) {
        return encode("SHA-256", message);
    }

    /**
     * 将摘要信息转换成SHA-512编码
     *
     * @param message 摘要信息
     * @return SHA-512编码之后的字符串
     */
    public static String sha512Encode(byte[] message) {
        return encode("SHA-512", message);
    }


    public static void main(String[] args) {
        EntityHelper.print(sha256Encode(""));
        EntityHelper.print(sha512Encode(""));
        EntityHelper.print(md5Encode(""));
    }


}
