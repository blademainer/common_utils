package com.xiongyingqi.util;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/7/22 0022.
 */
public class NumberHelper {
    public static int parseStringToInt(String s) {
        try {
            return Integer.valueOf(s);
        } catch (Exception e) {
            return 0;
        }
    }

    public static double parseStringToDouble(String s) {
        try {
            return Double.valueOf(s);
        } catch (Exception e) {
            return 0;
        }
    }

    public static float parseStringToFloat(String s) {
        try {
            return Float.valueOf(s);
        } catch (Exception e) {
            return 0;
        }
    }

}
