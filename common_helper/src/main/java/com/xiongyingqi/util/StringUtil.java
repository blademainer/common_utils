package com.xiongyingqi.util;

import java.util.Random;

public class StringUtil {
    private static char[] letters;
    private static long nextLong;
    private static int longLength = numberLength(Long.MAX_VALUE);
    private static int nextInt;
    private static int intLength = numberLength(Integer.MAX_VALUE);

    private static int numberLength(long number) {
        int i = 0;
        for (; number > 0; number = number / 10) {
            i++;
        }
        return i;
    }

    static {
        letters = new char[62];
        int j = 0;
        for (int i = 0, k = 'A'; i < 26; i++) {
            letters[j++] = (char) (k + i);
        }
        for (int i = 0, k = 'a'; i < 26; i++) {
            letters[j++] = (char) (k + i);
        }
        for (int i = 0, k = '0'; i < 10; i++) {
            letters[j++] = (char) (k + i);
        }
    }

    public static String randomString() {
        return randomString(8);
    }

    public static String randomString(int length) {
        char[] strChar = new char[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            strChar[i] = letters[random.nextInt(letters.length)];
        }
        String randomString = new String(strChar);
        return randomString;
    }

    /**
     * 返回下一个数字，并且跟最大数字的字符串长度相等 <br>
     * 2013-10-31 下午2:43:11
     *
     * @return
     */
    public static String nextInt() {
        synchronized (ThreadHelper.LOCK) {
            return fillZero(nextInt++, intLength);
        }
    }

    /**
     * 返回下一个数字，并且跟最大数字的字符串长度相等 <br>
     * 2013-10-31 下午2:43:11
     *
     * @return
     */
    public static String nextLong() {
        synchronized (ThreadHelper.LOCK) {
            return fillZero(nextLong++, longLength);
        }
    }

    /**
     * 填充0 <br>
     * 2013-10-31 下午2:46:15
     *
     * @param number
     * @param length
     * @return
     */
    public static String fillZero(long number, int length) {
        StringBuilder builder = new StringBuilder();
        int least = length - numberLength(number);
        char[] zeros = new char[least];
        for (int i = 0; i < least; i++) {
            zeros[i] = '0';
        }
        builder.append(new String(zeros));
        builder.append(number);
        return builder.toString();
    }

    public static void main(String[] args) {
        //		System.out.println(randomString());
        //		System.out.println(numberLength(100));
        //		System.out.println(numberLength(Long.MAX_VALUE));

        byte a = 1;
        byte b = 1 << 1;
        byte c = (byte) (a | b);
        System.out.println(c & a);

    }
}
