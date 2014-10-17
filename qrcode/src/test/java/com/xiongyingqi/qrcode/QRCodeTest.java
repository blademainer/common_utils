package com.xiongyingqi.qrcode;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.UUID;

public class QRCodeTest {
    private Profile profile;

    @Before
    public void setUp() throws Exception {
        String logoPath = getClass().getClassLoader().getResource("logo.jpg").getFile();
        String folder = new File(logoPath).getParent();

        profile = new Profile();
        profile.setContent("http://xiongyingqi.com");
        profile.setCharacterSet("UTF-8");
        profile.setErrorCorrectionLevel(ErrorCorrectionLevel.H);
        profile.setLogoFlag(true);
        profile.setFormat("png");
        profile.setMargin(0);
        profile.setWidth(300);
        profile.setHeight(300);
        profile.setPath(folder);
        profile.setLogoPath(logoPath);
    }

    @Test
    public void testEncode() throws Exception {
        QRCode qrCode = new QRCode();
        File file = qrCode.encode(profile);
        System.out.println("生成二维码: " + file);
        String decode = qrCode.decode(file.getPath());
        Assert.assertEquals(decode, profile.getContent());
    }


    @Test
    public void testEncodeToOutputStream() throws Exception {
        File file = new File(profile.getPath(), UUID.randomUUID().toString() + ".png");
        QRCode qrCode = new QRCode();
        OutputStream outputStream = qrCode.encodeToOutputStream(profile);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) outputStream;
        byte[] bytes = byteArrayOutputStream.toByteArray();
        fileOutputStream.write(bytes);
        System.out.println("生成二维码: " + file);
        Assert.assertNotNull(file);
        String decode = qrCode.decode(file.getPath());
        Assert.assertEquals(decode, profile.getContent());
    }

    @Test
    public void testDecode() throws Exception {
        QRCode qrCode = new QRCode();
        File file = qrCode.encode(profile);
        System.out.println("生成二维码: " + file);
        String decode = qrCode.decode(file.getPath());
        Assert.assertEquals(decode, profile.getContent());
    }
}