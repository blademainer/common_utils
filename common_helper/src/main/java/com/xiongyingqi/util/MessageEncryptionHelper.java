package com.xiongyingqi.util;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

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

    /**
     * 加密方法
     * <pre>
     * // 1.1  首先要创建一个密匙
     * // DES算法要求有一个可信任的随机数源
     * SecureRandom sr = new SecureRandom();
     * // 为我们选择的DES算法生成一个KeyGenerator对象
     * KeyGenerator kg = KeyGenerator.getInstance("DES");
     * kg.init(sr);
     * // 生成密匙
     * SecretKey key = kg.generateKey();
     * // 获取密匙数据
     * byte rawKeyData[] = key.getEncoded();
     * //        byte rawKeyData[] = "sucretsa".getBytes();
     * System.out.println("密匙长度===" + rawKeyData.length);
     * System.out.println("密匙Base64===" + Base64.encodeBytes(rawKeyData));
     *
     * String str = "hi.baidu.com/beijingalana"; // 待加密数据
     * // 2.1  调用加密方法
     * byte[] encryptedData = desEncrypt(rawKeyData, str.getBytes());
     * // 3.1  调用解密方法
     * byte[] decrypt = desDecrypt(rawKeyData, encryptedData);
     * System.out.println("解密===" + new String(decrypt));
     * </pre>
     *
     * @param rawKeyData 密钥
     * @param data       要加密的数据
     * @throws java.security.InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public static byte[] desEncrypt(byte rawKeyData[], byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException,
            NoSuchPaddingException, InvalidKeySpecException {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        // 现在，获取数据并加密
//        byte data[] = str.getBytes();
        // 正式执行加密操作
        byte[] encryptedData = cipher.doFinal(data);
//        System.out.println("加密后===>" + encryptedData);
        return encryptedData;
    }

    /**
     * 解密方法
     *
     * @param rawKeyData
     * @param encryptedData
     * @return byte[]
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     */
    public static byte[] desDecrypt(byte rawKeyData[], byte[] encryptedData)
            throws IllegalBlockSizeException, BadPaddingException,
            InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeySpecException {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, key, sr);
        // 正式执行解密操作
        byte decryptedData[] = cipher.doFinal(encryptedData);
//        System.out.println("解密后===>" + new String(decryptedData));
        return decryptedData;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException {
        EntityHelper.print(sha256Encode(""));
        EntityHelper.print(sha512Encode(""));
        EntityHelper.print(md5Encode(""));


        // 1.1 >>> 首先要创建一个密匙
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 为我们选择的DES算法生成一个KeyGenerator对象
        KeyGenerator kg = KeyGenerator.getInstance("DES");
        kg.init(sr);
        // 生成密匙
        SecretKey key = kg.generateKey();
        // 获取密匙数据
        byte rawKeyData[] = key.getEncoded();
//        byte rawKeyData[] = "sucretsa".getBytes();
        System.out.println("密匙长度===>" + rawKeyData.length);
        System.out.println("密匙Base64===>" + Base64.encodeBytes(rawKeyData));

        String str = "hi.baidu.com/beijingalana"; // 待加密数据
        // 2.1 >>> 调用加密方法
        byte[] encryptedData = desEncrypt(rawKeyData, str.getBytes());
        encryptedData = desEncrypt(rawKeyData, encryptedData);
        // 3.1 >>> 调用解密方法
        byte[] decrypt = desDecrypt(rawKeyData, encryptedData);
        decrypt = desDecrypt(rawKeyData, decrypt);
        System.out.println("解密===>" + new String(decrypt));
    }


}
