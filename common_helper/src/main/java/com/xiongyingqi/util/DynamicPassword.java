package com.xiongyingqi.util;

import java.util.Random;

/**
 * 基于密钥和时间生成动态密码
 *
 * @author xiongyingqi <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2014-2-11 下午9:08:39
 */
public class DynamicPassword {
    /**
     * 密钥长度
     */
    public static final int KEY_LENGTH = 16;
    /**
     * 最小单位（秒）
     */
    private static final int SECOND = 1000;
    /**
     * 默认密码刷新时间
     */
    public static final int DEFAULT_REFRESH_TIME = 4;

    /**
     * 随机生成指定长度字节数的密钥
     * <br>2014-2-11 下午9:10:17
     *
     * @param length
     * @return byte[]
     */
    public byte[] generateKey(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("长度为：" + length + "， 指定的密钥长度错误！");
        }
        byte[] bts = new byte[length];

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            bts[i] = (byte) random.nextInt(255);
        }
        return bts;
    }


    public byte[] getTimeBytes() {
        return getTimeBytes(DEFAULT_REFRESH_TIME);
    }

    /**
     * 根据指定的时间刷新周期获取时间字节
     * <br>2014-2-11 下午9:13:21
     *
     * @param seconds
     * @return
     */
    public byte[] getTimeBytes(int seconds) {
        if (seconds <= 0) {
            throw new IllegalArgumentException("秒数为：" + seconds
                    + "， 指定的密钥更新周期错误！");
        }
        long currentTimeMillis = System.currentTimeMillis();
        // System.out.println(currentTimeMillis);
        // 对秒数取整，比如seconds=30，那么21点00分18秒与21点00分29秒是相同的结果
        currentTimeMillis /= seconds * SECOND;

        // long currentTimeMillisRs = currentTimeMillis * seconds *
        // SECOND;//还原时间位数
        // System.out.println(currentTimeMillisRs);
        // Date date = new Date(currentTimeMillisRs);
        // String dateStr = DateHelper.FORMATTER_LONG.format(date);
        // System.out.println(dateStr);

        return ByteHelper.longToBytes(currentTimeMillis);
    }

    /**
     * 对字节进行迭代运算，这样结果就不具备规律性
     * <br>2014-2-11 下午9:14:18
     *
     * @param key
     * @return
     */
    public byte[] generateCode(byte[] key, int refreshTime) {
        byte[] bs = getTimeBytes(refreshTime);
        int length = bs.length;
        for (int i = 0; i < length; i++) {
            byte b = bs[i];
            for (int j = 0; j < length; j++) {
                bs[i] = (byte) (bs[j] | b);
                bs[i] = (byte) (bs[j] ^ b);
            }
        }

        int keyLength = key.length;
        byte[] rs = new byte[keyLength];
        System.arraycopy(key, 0, rs, 0, keyLength);
        for (int i = 0; i < keyLength; i++) {
            byte k = rs[i];
            for (int j = 0; j < length; j++) {
                rs[i] = (byte) (bs[j] ^ k);
                rs[i] = (byte) (bs[j] | k);
            }
        }
        // String string = Base64.encodeBytes(rs);
        // System.out.println(string);
        return rs;
    }

    public long bytesToLong(byte[] bts) {
        int inputLength = bts.length;
        long data = 0;
        long temp = 0;
        for (int i = 0; i < inputLength; i++) {
            data <<= 8;
            byte b = bts[i];
            temp = b & 0xFF;
            data |= temp;
        }
        return data;
    }

    public byte[] compressBytes(byte[] bts, int length) {
        int inputLength = bts.length;
        if (inputLength < length) {
            throw new ArrayIndexOutOfBoundsException("无法压缩：目标参数length小于bts的长度！");
        }

        float multiple = inputLength / length;// 倍数
        int leastLength = inputLength % length;// 剩余字节数

        int index = 0;

        int i = 0;
        byte[] rs = new byte[length];
        while (i < length) {
            byte bt = 0;
            for (int j = 0; j < multiple; j++) {
                bt += bts[index++];
            }
            rs[i++] = bt;
        }

        for (int j = 0; j < length; j++) {
            for (int k = 0; k < leastLength; k++) {
                rs[j] ^= bts[index + k];
            }
        }
//	System.out.println("rs =========== " + rs.length);
//	System.out.println("index ========== " + index);
//	System.out.println("leastLength ========== " + leastLength);
//	System.out.println("multiple ========== " + multiple);
//	System.out.println("inputLength ========== " + inputLength);
//	System.out.println("length ========== " + length);

        return rs;
    }

    public void printBytes(byte[] bts) {
        for (int i = 0; i < bts.length; i++) {
            byte b = bts[i];
            System.out.println(b);
        }
    }

    public long generatePassword(byte[] key, int refreshTime) {
        int minApproach = minApproach(6);
        int i = minApproach / 8;
        if (minApproach % 8 > 0) {
            i++;
        }

        byte[] rs = generateCode(key, refreshTime);
        byte[] data = compressBytes(rs, i);
//	printBytes(data);
//	String string = Base64.encodeBytes(data);
//	System.out.println(string);
        long code = bytesToLong(data);
        long l = (long) (code & 999999);// 最大的为999999
        return l;
    }

    public static void main(String[] args) {
//	double a = (1 << 16) / (Math.pow(10, 6) - 1);
//
//        final byte[] key = generateKey(KEY_LENGTH);
//        Long keyL = ByteHelper.bytesToLong(key);
//        byte[] keys = ByteHelper.longToBytes(keyL);
//        Long keyLs = ByteHelper.bytesToLong(keys);
//        EntityHelper.print(keyL);
//        EntityHelper.print(keyLs);

        for (int i = 0; i < 10; i++) {
            final int j = i;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    DynamicPassword dynamicPassword = new DynamicPassword();
                    final byte[] key = dynamicPassword.generateKey(KEY_LENGTH);
                    while (true) {
                        System.out.println(j + "当前动态密码：" + dynamicPassword.generatePassword(key, DEFAULT_REFRESH_TIME));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            thread.start();
        }


//	long s = bytesToLong(new byte[]{ (byte) 255, (byte) 255 });
//	System.out.println(s);
    }

    /**
     * 获取最接近digit位数字的二进制表示的数（一定大于digit位的数字）的大小
     * 例如，如果digit为6，那么表示999999最接近的二进制数字为1048576(2的20次方)，结果返回20
     * <br>2014-1-21 下午5:27:53
     *
     * @param digit
     * @return
     */
    public static int minApproach(int digit) {
        int max = (int) (Math.pow(10, digit) - 1);
//	System.out.println(max);
        int i = 0;
        while (max > 0) {
            max >>= 1;
            i++;
        }
//	int k = 1 << i;
//	System.out.println(k);
//	System.out.println(i);
        return i;
    }
}
