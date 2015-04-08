package com.xiongyingqi.util;

/**
 * @author xiongyingqi <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2014-1-21 上午9:56:22
 */
public class ByteHelper {
    /**
     * All possible chars for representing a number as a String
     */
    final static char[] digits = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };

    final static char[] letters = new char[255];

    static {
        char first = '0';
        for (int i = 0; i < letters.length; i++) {
            char c = (char) (first + i);
            EntityHelper.print(c);
        }
    }

    /**
     * Convert the integer to an unsigned number.
     */
    private static String toUnsignedString(int i, int shift) {
        char[] buf = new char[32];
        int charPos = 32;
        int radix = 1 << shift;
        int mask = radix - 1;
        do {
            buf[--charPos] = digits[i & mask];
            i >>>= shift;
        } while (i != 0);

        return new String(buf, charPos, (32 - charPos));
    }

    public static void bytesToOct(byte[] bts) {

    }


    public static byte[] intToBytes(int i) {
        byte[] b = new byte[4];
        b[0] = (byte) (0xff & i);
        b[1] = (byte) ((0xff00 & i) >> 8);
        b[2] = (byte) ((0xff0000 & i) >> 16);
        b[3] = (byte) ((0xff000000 & i) >> 24);
        return b;
    }

    public static int bytesToInt(byte[] bytes) {
        int num = bytes[0] & 0xFF;
        num |= ((bytes[1] << 8) & 0xFF00);
        num |= ((bytes[2] << 16) & 0xFF0000);
        num |= ((bytes[3] << 24) & 0xFF000000);
        return num;
    }

    public static long bytesToLong(byte[] b) {
        long temp = 0;
        long res = 0;
        for (int i = 0; i < b.length; i++) {
            res <<= 8;
            temp = b[i] & 0xff;
            res |= temp;
        }
        return res;
    }

    public static byte[] longToBytes(long num) {
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) (num >>> (56 - (i * 8)));
        }
        return b;
    }

    public static void main(String[] args) {
        System.out.println(Integer.toOctalString(124124));
        System.out.println(toUnsignedString(2, 1));
    }
}
