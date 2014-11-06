package com.xiongyingqi.util;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

public class FileHelperTest {
    private File excelFile;

    private File textPlainFile;
    private File textPlainUtf8File;

    @Before
    public void setUp() throws Exception {
        excelFile = new File(getClass().getClassLoader().getResource("ths.xls").getFile());
        textPlainFile = new File(getClass().getClassLoader().getResource("test.txt").getFile());
        textPlainUtf8File = new File(getClass().getClassLoader().getResource("test_utf8.txt").getFile());
    }

    @Test
    public void testCheckFileSizeLessThen() throws Exception {

    }

    @org.junit.Test
    public void testAppendStringToFile() throws Exception {
        String filePath = getClass().getClassLoader().getResource("").getFile();
        File file = new File(filePath, "appendTest.txt");
        file.delete();
        FileHelper.appendStringToFile(file, "a");
        FileHelper.appendStringToFile(file, "b");
        FileHelper.appendStringToFile(file, "c");
        String s = FileHelper.readFileToString(file);
        org.junit.Assert.assertEquals(s, "abc");
    }

    @org.junit.Test
    public void testAppendBytesToFile() throws Exception {
        String filePath = getClass().getClassLoader().getResource("").getFile();
        File file = new File(filePath, "appendTest.txt");
        file.delete();
        FileHelper.appendBytesToFile(file, "a".getBytes());
        FileHelper.appendBytesToFile(file, "b".getBytes());
        FileHelper.appendBytesToFile(file, "c".getBytes());
        String s = FileHelper.readFileToString(file);
        org.junit.Assert.assertEquals(s, "abc");
    }

    @Test
    public void testGetMimeType() throws Exception {

    }

    @Test
    public void testListFilesBySuffix() throws Exception {

    }

    @Test
    public void testReadBufferImage() throws Exception {

    }

    @Test
    public void testReadURL() throws Exception {

    }

    @Test
    public void testReadInputStreamToString() throws Exception {
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
//        byte[] data = "asdfasdfasgasgd".getBytes();
//        buffer.put(data);
//        buffer.put(data);
////        buffer.limit(data.length * 2);
//        buffer.flip();
//
//        byte[] bts = new byte[data.length * 2];
//        ByteBuffer byteBuffer = buffer.get(bts, 0, data.length * 2);
//        EntityHelper.print(bts.length);
//        EntityHelper.print(new String(bts));
//        EntityHelper.print(EntityHelper.arrayEquals(data, bts));

        EntityHelper.print(FileHelper.getEncode(excelFile));
        EntityHelper.print(FileHelper.readInputStreamToString(new FileInputStream(excelFile), FileHelper.getEncode(excelFile)));
        EntityHelper.print(FileHelper.readInputStreamToString(new FileInputStream(textPlainUtf8File), FileHelper.getEncode(textPlainUtf8File)));
    }

    @Test
    public void testReadInputStreamToString1() throws Exception {

    }

    @Test
    public void testOpenStream() throws Exception {

    }

    @Test
    public void testOpenReader() throws Exception {

    }

    @Test
    public void testOpenBufferedReader() throws Exception {

    }

    @Test
    public void testReadInputStreamToString2() throws Exception {

    }

    @Test
    public void testReadInputStream() throws Exception {

    }

    @Test
    public void testToURL() throws Exception {

    }

    @Test
    public void testReadInputStream1() throws Exception {

    }

    @Test
    public void testReadInputStream2() throws Exception {

    }

    @Test
    public void testReadFileToString() throws Exception {

    }

    @Test
    public void testReadFileToString1() throws Exception {

    }

    @Test
    public void testGetEncode() throws Exception {

    }

    @Test
    public void testGetEncodeByUtil() throws Exception {
        EntityHelper.print(FileHelper.getEncode(textPlainFile));
        EntityHelper.print(FileHelper.getEncode(textPlainUtf8File));
    }

    @Test
    public void testValidateFile() throws Exception {

    }

    @Test
    public void testCopyFile() throws Exception {

    }

    @Test
    public void testCopyFile1() throws Exception {

    }

    @Test
    public void testCopyFile2() throws Exception {

    }

    @Test
    public void testCopyFile3() throws Exception {

    }

    @Test
    public void testCutFile() throws Exception {

    }

    @Test
    public void testFileNotFullAndExists() throws Exception {

    }

    @Test
    public void testReadFileToBytes() throws Exception {

    }

    @Test
    public void testWriteBytesToFile() throws Exception {

    }

    @Test
    public void testWriteStringToFile() throws Exception {

    }

    @Test
    public void testSerializeObjectToFile() throws Exception {

    }

    @Test
    public void testCloseInputStream() throws Exception {

    }

    @Test
    public void testCloseOutStream() throws Exception {

    }

    @Test
    public void testUnSerializeObjectFromFile() throws Exception {

    }

    @Test
    public void testMain() throws Exception {

    }

    @Test
    public void testCloseReader() throws Exception {

    }

    @Test
    public void testCloseWriter() throws Exception {

    }

    @Test
    public void testFlushAndCloseWriter() throws Exception {

    }
}