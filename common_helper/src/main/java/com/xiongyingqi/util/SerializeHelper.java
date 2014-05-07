package com.xiongyingqi.util;

import java.io.*;
import java.security.Key;
import java.util.Date;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/4/28 0028.
 */
public class SerializeHelper {
    private static byte index;

    /**
     * 将对象序列化到文件
     *
     * @param folder       父目录
     * @param serializable
     * @return
     * @throws java.io.IOException
     */
    public static File writeObjectToFile(File folder, Serializable serializable) throws IOException {
        Assert.notNull(folder, "文件夹不能为空");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Assert.isTrue(folder.isDirectory(), folder + "不是文件夹");
        File file = new File(folder, System.currentTimeMillis() + nextIndex() + "");
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
        outputStream.writeObject(serializable);
        outputStream.flush();
        outputStream.close();
        return file;
    }

    public static byte[] readObjectToBytes(Serializable serializable) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(arrayOutputStream);
            outputStream.writeObject(serializable);
            outputStream.flush();
            outputStream.close();
            arrayOutputStream.flush();
            arrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayOutputStream.toByteArray();
    }

    /**
     * 返回固定长度的
     *
     * @return
     */
    private static String nextIndex() {
        String rs = "";
        synchronized (SerializeHelper.class) {
            rs += ++index < 0 ? -index : index;
            int least = 3 - rs.length();
            for (int i = 0; i < least; i++) {
                rs = "0" + rs;
            }
            return rs;
        }
    }

    public static long getTimeOfTheName(String fileName) throws NumberFormatException{
        String longStr = fileName.substring(0, fileName.length() - 3);
        Long lo = Long.parseLong(longStr);
        return lo;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            EntityHelper.print(nextIndex());
            String fileName = System.currentTimeMillis() + nextIndex();
            EntityHelper.print(getTimeOfTheName(fileName));
        }

        System.out.println(Base64.decodeToObject(Base64.encodeObject(new Date())));
    }

    public static Key readObjectFromFile(File privateKeyFile) throws IOException, ClassNotFoundException {
        Assert.notNull(privateKeyFile, "文件夹不能为空");
        Assert.isTrue(privateKeyFile.exists(), "私钥文件不存在");
        InputStream inputStream = new FileInputStream(privateKeyFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Key privateKey = (Key) objectInputStream.readObject();
        return privateKey;
    }
}
