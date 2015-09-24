/**
 * spark_src
 */
package com.xiongyingqi.util;

import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @author XiongYingqi
 * @version 2013-6-24 下午10:47:33
 */
public class FileHelper {
    public static final Logger LOGGER = Logger.getLogger(FileHelper.class);

    public static final long KB = 1024;
    public static final long MB = 1024 * KB;
    public static final long GB = 1024 * MB;
    public static final long TB = 1024 * GB;
    public static final long PB = 1024 * TB;

    //	public static final long EB = 1024 * PB;
    //	public static final long ZB = 1024 * EB;
    //	public static final long YB = 1024 * ZB;
    //	public static final long BB = 1024 * YB;
    //	public static final long NB = 1024 * BB;
    //	public static final long DB = 1024 * NB;

    public static boolean checkFileSizeLessThen(File file, long size) {
        try {
            fileNotFullAndExists(file);
        } catch (Exception e) {
            return false;
        }

        return file.length() <= size;
    }

    public static File appendStringToFile(File file, String string) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            fileWriter.append(string);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File appendBytesToFile(File file, byte[] bts) {
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file, true));
            outputStream.write(bts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 获取文件类型 <br>
     * 2013-9-2 下午9:08:51
     *
     * @param data
     * @return
     */
    public static String getMimeType(File data) {
        if (!data.exists()) {
            return null;
        }
        ContentHandler contenthandler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        metadata.set(Metadata.RESOURCE_NAME_KEY, data.getName());
        Parser parser = new AutoDetectParser();
        try {
            parser.parse(new FileInputStream(data), contenthandler, metadata, null);
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (SAXException e) {
            LOGGER.error(e);
        } catch (TikaException e) {
            LOGGER.error(e);
        }
        // System.out.println("Mime: " + metadata.get(Metadata.CONTENT_TYPE));
        // System.out.println("Mime str: " + metadata.toString());
        return metadata.get(Metadata.CONTENT_TYPE);
    }

    /**
     * 根据后缀名从文件夹获取文件 <br>
     * 2013-9-6 下午3:15:56
     *
     * @param folder
     * @param suffix
     * @return
     */
    public static File[] listFilesBySuffix(File folder, final String suffix) {
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File paramFile, String paramString) {
                String newSuffix = suffix;
                if (!newSuffix.startsWith(".")) {
                    newSuffix = "." + newSuffix;
                }
                if (paramString.endsWith(newSuffix)) {
                    return true;
                }
                return false;
            }
        });
        return files;
    }

    public static File readBufferImage(BufferedImage bufferedImage) {
        File file = null;
        FileOutputStream outputStream = null;
        try {
            file = File.createTempFile(System.currentTimeMillis() + StringUtil.randomString(8),
                    "gif");
            outputStream = new FileOutputStream(file);
            ImageIO.write(bufferedImage, "gif", outputStream);
        } catch (IOException e) {
            LOGGER.error(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    LOGGER.error(e);
                }
            }
        }
        return file;
    }

    /**
     * 从url地址读取文件信息并写入到临时文件 <br>
     * 2013-9-2 下午2:57:15
     *
     * @param urlStr
     * @return
     * @throws java.io.IOException
     */
    public static File readURL(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        // System.out.println(" ----------------- url ----------------- ");
        // System.out.println(urlStr);
        // System.out.println(url.getPath());
        // System.out.println(url.getHost());
        // System.out.println(" ---------------------------------- ");
        InputStream inputStream = null;
        if (url.getHost().trim().equals("")) {
            // inputStream = new FileInputStream(url.getFile());
            File file = new File(url.getFile());
            return file;
        } else {
            inputStream = url.openStream();
        }
        return readInputStream(inputStream);
    }

    public static String readInputStreamToString(InputStream inputStream, Charset charset) {
        byte[] bts = null;
        try {
            bts = readInputStreamToBytes(inputStream);
            if (bts == null) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = new String(bts, charset);
        return content;

//        String rs = null;
//        try {
//            Reader reader = new InputStreamReader(inputStream, charset);
//            BufferedReader bufferedReader = new BufferedReader(reader);
//            StringBuilder builder = new StringBuilder();
//            String line = null;
//            while ((line = bufferedReader.readLine()) != null) {
//                builder.append(line);
//            }
//            rs = builder.toString();
//
////            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024 * 1024);
////            int length = -1;
////            int bufferSize = 128;
////            byte[] buffer = new byte[bufferSize];
////            while ((length = inputStream.read(buffer)) != -1) {
////                if (length != bufferSize) {
////                    System.arraycopy(buffer, 0, buffer, 0, length);
////                }
////                EntityHelper.print(new String(buffer));
////                byteBuffer.put(buffer);
////            }
////            rs = new String(byteBuffer.array(), encoding);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            // try {
//            // inputStream.close();
//            // } catch (IOException e1) {
//            // e1.printStackTrace();
//            // }
//            try {
//                inputStream.close();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
//        return rs;
    }

    public static String readInputStreamToString(InputStream inputStream, String encoding) {
        return readInputStreamToString(inputStream, Charset.forName(encoding));
    }


    public static InputStream openStream(File file) {
        Assert.isTrue(file.exists(), "文件：" + file + "不存在！");
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public static Reader openReader(File file) {
        Assert.isTrue(file.exists(), "文件：" + file + "不存在！");
        Reader reader = null;
        try {
            reader = new FileReader(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return reader;
    }

    public static BufferedReader openBufferedReader(File file) {
        BufferedReader bufferedReader = new BufferedReader(openReader(file));
        return bufferedReader;
    }

    public static String readInputStreamToString(InputStream inputStream) {
        return readInputStreamToString(inputStream, "UTF-8");
    }

//    public static String readInputStreamToString(InputStream inputStream, String encoding) {
//        String rs = null;
//        try {
//            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024 * 1024);
//            int length = -1;
//            int bufferSize = 128;
//            byte[] buffer = new byte[bufferSize];
//            while ((length = inputStream.read(buffer)) != -1) {
//                if (length != bufferSize) {
//                    System.arraycopy(buffer, 0, buffer, 0, length);
//                }
//                byteBuffer.put(buffer);
//            }
//            rs = new String(byteBuffer.array(), encoding);
//        } catch (IOException e) {
//            LOGGER.error(e);
//        } finally {
//            // try {
//            // inputStream.close();
//            // } catch (IOException e1) {
//            // e1.printStackTrace();
//            // }
//            try {
//                inputStream.close();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
//        return rs;
//    }

    /**
     * 从流读取文件内容 <br>
     * 2013-9-2 下午2:52:39
     *
     * @param inputStream
     * @return
     */
    public static File readInputStream(InputStream inputStream) {
        String filePath = System.getProperty("java.io.tmpdir", "tmp/");
        filePath += System.currentTimeMillis() + StringUtil.randomString(8) + ".tmp";
        return readInputStream(inputStream, filePath);
    }

    /**
     * 将文件转换为URL地址 <br>
     * 2013-9-2 下午10:07:14
     *
     * @param file
     * @return
     */
    public static URL toURL(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        URI uri = file.toURI();
        URL url = null;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            LOGGER.error(e);
        }
        return url;
    }

    /**
     * 将流读入到文件内 <br>
     * 2013-9-2 下午10:04:17
     *
     * @param inputStream
     * @param file
     * @return
     */
    public static File readInputStream(InputStream inputStream, File file) {
        try {
            validateFile(file);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            int length = -1;
            try {
                int bufferSize = 128;
                byte[] buffer = new byte[bufferSize];
                while ((length = inputStream.read(buffer)) != -1) {
                    if (length != bufferSize) {
                        System.arraycopy(buffer, 0, buffer, 0, length);
                    }
                    outputStream.write(buffer);
                }
            } catch (IOException e) {
                LOGGER.error(e);
            } finally {
                // try {
                // inputStream.close();
                // } catch (IOException e1) {
                // e1.printStackTrace();
                // }
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    LOGGER.error(e);
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    LOGGER.error(e);
                }
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }
        return file;
    }

    public static File readInputStream(InputStream inputStream, String path) {
        File file = new File(path);
        return readInputStream(inputStream, file);
    }

    /**
     * 读取指定路径的文件内容（以String的形式） <br>
     * 2013-8-30 下午5:28:25
     *
     * @param fileName
     * @return
     */
    public static String readFileToString(String fileName) {
        return readFileToString(new File(fileName));
    }

    /**
     * 读取指定文件的文件内容（以String的形式） <br>
     * 2013-8-30 下午5:26:55
     *
     * @param file
     * @return
     */
    public static String readFileToString(File file) {
        byte[] bts = null;
        try {
            bts = readFileToBytes(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = null;
        try {
            content = new String(bts, getEncode(file));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 获取文本文档的编码
     * <br>2013年12月5日 下午5:27:07
     *
     * @param file
     * @return
     */
//    public static String getEncode(File file) {
//        InputStream inputStream = null;
//        String code = "UTF-8";
//        try {
//            inputStream = new FileInputStream(file);
//            byte[] head = new byte[3];
//            inputStream.read(head);
//            if (head[0] == -1 && head[1] == -2) {
//                code = "UTF-16";
//            }
//            if (head[0] == -2 && head[1] == -1) {
//                code = "Unicode";
//            }
//            if (head[0] == -17 && head[1] == -69 && head[2] == -65) {
//                code = "UTF-8";
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return code;
//    }

    /**
     * 获取文本文档的编码
     * <br>2013年12月5日 下午5:27:07
     *
     * @param file
     * @return
     */
    public static String getEncode(File file) {
        return FileEncode.getEncodeByUtil(file);
    }

    public static File validateFile(File file) throws Exception {
        if (file != null) {
            if (file.isDirectory()) {
                if (!file.exists()) {
                    file.mkdirs();
                }
            } else {
                File parentFolder = file.getParentFile();
                if (parentFolder != null && !parentFolder.exists()) {
                    parentFolder.mkdirs();
                }
                file.createNewFile();
            }
        } else {
            throw new Exception("传入 的引用为null！");
        }
        return file;
    }

    public static void copyFile(String originFilePath, String targetFilePath) {
        File originFile = new File(originFilePath);
        File targetFile = new File(targetFilePath);
        copyFile(originFile, targetFile);
    }

    public static void copyFile(URL originURL, File targetFile) {
        try {
            copyFile(originURL.openStream(), targetFile);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public static void copyFile(InputStream inputStream, File targetFile) {
        if (inputStream == null) {
            return;
        }
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(targetFile);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        byte[] buffer = new byte[1024 * 1024]; // 1M的缓存
        try {
            int length = inputStream.read(buffer);
            while (length > 0) {
                byte[] targetBuffer = new byte[length];
                System.arraycopy(buffer, 0, targetBuffer, 0, length);
                outputStream.write(targetBuffer);
                length = inputStream.read(buffer);
            }
        } catch (IOException e) {
            LOGGER.error(e);
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
    }

    public static void copyFile(File originFile, File targetFile) {

        if (originFile.length() == targetFile.length()) {
            return;
        }
        try {
            validateFile(targetFile);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(originFile);

            outputStream = new FileOutputStream(targetFile);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        // System.out.println("Copy file: " + originFile);
        // System.out.println("Target file: " + targetFile);
        byte[] buffer = new byte[1024 * 1024]; // 1M的缓存
        try {
            int length = inputStream.read(buffer);
            while (length > 0) {
                byte[] targetBuffer = new byte[length];
                System.arraycopy(buffer, 0, targetBuffer, 0, length);
                outputStream.write(targetBuffer);
                length = inputStream.read(buffer);
            }
        } catch (IOException e) {
            LOGGER.error(e);
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }

    }


    /**
     * 将源文件剪切到目标文件
     *
     * @param originFile 源文件
     * @param targetFile 目标文件
     * @return
     */
    public static boolean cutFile(File originFile, File targetFile) {
        Assert.notNull(originFile);
        Assert.notNull(targetFile);
        copyFile(originFile, targetFile);
        if (originFile.length() == targetFile.length()) {
            return originFile.delete();
        }
        return false;
    }

    /**
     * 检查文件不为空且存在 <br>
     * 2013-6-24 下午10:52:54
     *
     * @param file
     * @throws Exception
     */
    public static void fileNotFullAndExists(File file) throws Exception {
        if (file == null || !file.exists()) {
            throw new Exception("文件不存在！");
        }
    }

    /**
     * 读取inputStream并返回字节数组 <br>
     * 2013-9-3 下午12:10:06
     *
     * @param inputStream
     * @return
     */
    public static byte[] readInputStreamToBytes(InputStream inputStream) throws Exception {
        byte[] data = null;
        try {
            int bufferSize = 128;
            byte[] buffer = new byte[bufferSize];
            int offset = 0;
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int length = -1;
            while ((length = inputStream.read(buffer)) != -1) {
                // if(length != bufferSize){
                // System.arraycopy(buffer, 0, data, offset, length);
                // }
//                    System.arraycopy(buffer, 0, data, offset, length);// 从缓冲区拷贝数组
                if (offset + length > byteBuffer.limit()) {
                    byteBuffer = growByteBuffer(byteBuffer, (int) (byteBuffer.limit() * 1.5));
                }
                byteBuffer.put(buffer, 0, length);
                offset += length;
            }
            byteBuffer.flip();
//            byteBuffer.limit(offset);
            data = new byte[offset];
            byteBuffer.get(data, 0, offset);
//            data = byteBuffer.array();
        } catch (IOException e) {
            LOGGER.error(e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return data;
    }

    public static ByteBuffer growByteBuffer(ByteBuffer byteBuffer, int size) {
        Assert.isTrue(size > byteBuffer.limit(), "扩容的大小小于已经存在的字节缓冲！");
        ByteBuffer byteBufferNew = ByteBuffer.allocate(size);
        byteBuffer.flip();
        byteBufferNew.put(byteBuffer);
        return byteBufferNew;
    }

    /**
     * 读取文件内容并返回字节数组 <br>
     * 2013-9-3 下午12:10:06
     *
     * @param iconFile
     * @return
     */
    public static byte[] readFileToBytes(File iconFile) throws Exception {
        try {
            validateFile(iconFile);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        long fileSize = iconFile.length();
        if (fileSize > Integer.MAX_VALUE) {
            throw new Exception("读取的文件过大！");
        }
        byte[] data = new byte[(int) fileSize];// 由于文件已经确定，因此大小也可以确定

        try {
            int length = -1;

            FileInputStream inputStream = new FileInputStream(iconFile);
            try {
                int bufferSize = 128;
                byte[] buffer = new byte[bufferSize];
                int offset = 0;
                while ((length = inputStream.read(buffer)) != -1) {
                    // if(length != bufferSize){
                    // System.arraycopy(buffer, 0, data, offset, length);
                    // }
                    System.arraycopy(buffer, 0, data, offset, length);// 从缓冲区拷贝数组
                    offset += length;
                }
            } catch (IOException e) {
                LOGGER.error(e);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }
        return data;
    }

    /**
     * 将字节组写入文件 <br>
     * 2013-9-3 下午2:39:21
     *
     * @param data
     * @param file
     */
    public static File writeBytesToFile(byte[] data, File file) {
        try {
            validateFile(file);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.error(e);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    LOGGER.error(e);
                }
            }
        }
        return file;

    }

    /**
     * 将字符串写入文件 <br>
     * 2013-9-6 下午12:09:45
     *
     * @param file
     * @param string
     * @return
     */
    public static File writeStringToFile(File file, String string) {
        try {
            byte[] bts = string.getBytes(getEncode(file));
            file = writeBytesToFile(bts, file);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ;
            LOGGER.error(e);
        }
        return file;
    }

    /**
     * 将对象写入文件 <br>
     * 2013-9-6 下午1:08:58
     *
     * @param targetFile
     * @param serializable
     */
    public static void serializeObjectToFile(File targetFile, Serializable serializable) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(targetFile);
            try {
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(serializable);
            } catch (IOException e) {
                LOGGER.error(e);
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        } finally {
            closeOutStream(objectOutputStream);
            closeOutStream(fileOutputStream);
        }
    }

    public static void closeInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
    }

    public static void closeOutStream(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.flush();
            } catch (IOException e) {
                LOGGER.error(e);
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
    }

    public static Object unSerializeObjectFromFile(File targetFile) {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        Object object = null;
        try {
            fileInputStream = new FileInputStream(targetFile);
            try {
                objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    object = objectInputStream.readObject();
                } catch (ClassNotFoundException e) {
                    LOGGER.error(e);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        } finally {
            closeInputStream(objectInputStream);
            closeInputStream(fileInputStream);
        }
        return object;
    }

    public static void main(String[] args) {
        // try {
        // File file = new File("tmp/a.html");
        // byte[] data = readFileToBytes(file);
        // String encodeStr = Base64.encodeBytes(data);
        // byte[] data2 = Base64.decode(encodeStr);
        // File rsfile = FileHelper.readBytesToFile(data2,
        // File.createTempFile(System.currentTimeMillis() +
        // StringUtil.randomString(8), ".html"));
        // System.out.println(rsfile);
        // System.out.println(encodeStr);
        // System.out.println(data.length);
        // System.out.println(file.length());
        // } catch (Exception e) {
        // LOGGER.error(e);
        // }

        // MessageVO messageVO = new MessageVO();
        // File objFile = new File("C:/Users/瑛琪/Desktop/b.obj");
        // serializeObjectToFile(objFile, messageVO);
        // Object object = unSerializeObjectFromFile(objFile);
        // System.out.println(object.equals(messageVO));
        //
        // File[] files = listFilesBySuffix(new File("C:/Users/瑛琪/Desktop/"),
        // "obj");
        // for (int i = 0; i < files.length; i++) {
        // File file = files[i];
        // System.out.println(file);
        // }
//        EntityHelper.print(getMimeType(new File(
//                "C:\\Users\\blade_000\\Desktop\\ad.html")));
//        EntityHelper.print(getEncodeByUtil(new File(
//                "D:\\workspace_JavaEE\\YIXUN_1.5\\YIXUN_1.5_ENTITY\\src\\main\\java\\com\\kingray\\domain\\bk2\\bk\\BizBank.java")));
//        EntityHelper.print(getEncode(new File(
//                "D:\\workspace_JavaEE\\YIXUN_1.5\\YIXUN_1.5_ENTITY\\src\\main\\java\\com\\kingray\\domain\\bk2\\bk\\BizBank.java")));
//        EntityHelper.print(getEncodeByUtil(new File(
//                "C:\\Users\\瑛琪\\Desktop\\ad.html")));
        File file = new File(
                "C:\\Users\\瑛琪\\Desktop\\test.txt");
        File file_bak = new File(
                "C:\\Users\\瑛琪\\Desktop\\test2.txt");
        String charset = getEncode(file);
        EntityHelper.print(charset);

        byte[] content = new byte[0];
        try {
            content = FileHelper.readFileToBytes(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            EntityHelper.print(new String(content, charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        FileHelper.writeBytesToFile(content, file_bak);

    }

    public static void closeReader(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeWriter(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void flushAndCloseWriter(Writer writer) {
        if (writer != null) {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean initFile(File file) {
        if (file == null) {
            return false;
        }
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            return parentFile.mkdirs();
        }
        return false;
    }
}
