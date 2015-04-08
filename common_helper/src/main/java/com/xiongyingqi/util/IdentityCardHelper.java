package com.xiongyingqi.util;

import java.io.*;

/**
 * 公民身份号码是由17位数字码和1位校验码组成。排列顺序从左至右分别为：6位地址码，8位出生日期码，3位顺序码和1位校验码。
 * 地址码（身份证地址码对照表见下面附录）和出生日期码很好理解，顺序码表示在同一地址码所标识的区域范围内，对同年同月同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性。
 * 身份证最后一位校验码算法如下：
 * 1. 将身份证号码前17位数分别乘以不同的系数，从第1位到第17位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
 * 2. 将得到的17个乘积相加。
 * 3. 将相加后的和除以11并得到余数。
 * 4. 余数可能为0 1 2 3 4 5 6 7 8 9 10这些个数字，其对应的身份证最后一位校验码为1 0 X 9 8 7 6 5 4 3 2。
 * 身份证号码解密|身份证尾数校验码算法|ID card information
 * <p/>
 * 身份证地址码对照表见id_address.txt
 * <p/>
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/4/15 0015.
 */
public class IdentityCardHelper {
    public static File ID_ADDRESS_FILE;
    public static final int[] numers = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    public static final char[] checkCodes = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    public static final String MALE = "男";
    public static final String FEMALE = "女";

    static {
        String filePath = IdentityCardHelper.class.getClassLoader().getResource("id_address.txt").getFile();
        ID_ADDRESS_FILE = new File(filePath);
    }

    public static String getSex(String number) {
        String indexString = number.substring(number.length() - 4, number.length() - 1);
        long l = Long.parseLong(indexString);
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            long n = l % 10;
            l = l / 10;
            sum += n;
        }
        if (sum % 2 == 1) {
            return MALE;
        } else {
            return FEMALE;
        }
    }


    public static boolean calculateVerifyCode(String number) {
        Assert.isTrue(number.length() == 18, "身份证长度不符！");
        String seventeen = number.substring(0, number.length() - 1);
        long l = Long.parseLong(seventeen);
        EntityHelper.print(l);

        int sum = 0;
        for (int i = 0; i < 17; i++) {
            long n = l % 10;
            l = l / 10;
            sum += n * numers[16 - i];
        }
        int verifyCode = sum % 11;
        char verifyCodeChar = checkCodes[verifyCode];
        EntityHelper.print(verifyCodeChar);
        if (number.substring(number.length() - 1).toCharArray()[0] == verifyCodeChar) {
            return true;
        }
        return false;
    }

    public static String readAddress(String id) {
        FileReader reader = null;
        try {
//            inputStream = new FileInputStream(ID_ADDRESS_FILE);
            reader = new FileReader(ID_ADDRESS_FILE);
            BufferedReader bufferedReader = new BufferedReader(reader);
            try {
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    String[] keyValue = line.split(" ");
                    if (keyValue[0].equals(id)) {
                        return keyValue[1];
                    }
//                    EntityHelper.print("key: " + keyValue[0]);
//                    EntityHelper.print("value: " + keyValue[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            FileHelper.closeReader(reader);
        }
        return null;
    }

    public static void main(String[] args) {
        EntityHelper.print(ID_ADDRESS_FILE);
        EntityHelper.print(readAddress(360423 + ""));
        EntityHelper.print(calculateVerifyCode("43012419870817777X"));
        EntityHelper.print(getSex("43012419870817777X"));
        EntityHelper.print(getSex("43012419870817767X"));
    }
}
