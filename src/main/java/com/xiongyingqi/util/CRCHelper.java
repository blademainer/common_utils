/**
 * YIXUN_1.5_EE
 */
package com.xiongyingqi.util;

/**
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-12-26 上午11:53:26
 */
public class CRCHelper {
    public static byte[] crc16(byte[] data) {
        if (data == null) {
            return data;
        }
        short dataIndex, bit;
        short rs = (short) 0xFFFF;// 初始化寄存器
        long length = data.length;// 目标数据大小（字节）
        for (int i = 0; i < length; i++)// 遍历数据内容
        {
            dataIndex = data[i];// 下一个数据
            rs = (short) (rs ^ dataIndex); // 将数据的下一个8bit字节与16bitCRC寄存器的低8bit进行异或，并把结果存入16bitCRC寄存器
            for (long j = 0; j < 8; j++) {
                bit = (short) (rs & 1);// 检查被移出的LSB
                rs = (short) ((rs >> 1) & 0x7FFF);// 16bitCRC寄存器向右移一位(MSB补零)
                if (bit != 0)// 若被移出的LSB为1
                {
                    rs = (short) (rs ^ 0xA001);// 16bitCRC寄存器与A001(hex)相异或
                }
            }
        }
        byte[] rsbts = new byte[2];// 初始化结果寄存器
        rsbts[1] = (byte) (rs >> 8);// 右移8位，存放高八位结果
        rsbts[0] = (byte) rs;// 存放低八位结果
        //System.Console.WriteLine("rsbts[0] ======== " + rsbts[0]);
        return rsbts;
    }

    public static void main(String[] args) {
        String a = "aaa";
        byte[] crc = crc16(a.getBytes());
        short rs = (short) (crc[0] + crc[1] << 8);
        System.out.println(rs);
    }
}
