package com.xiongyingqi.qrcode;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.IOException;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/10/17 0017.
 */
public class QRCodeGenerator {
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("java.io.tmpdir"));
    }

    private Profile profile;

    private QRCodeGenerator() {

    }

    public static QRCodeGenerator newGenerator() {
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
        qrCodeGenerator.profile = new Profile();
        qrCodeGenerator.characterSet("UTF-8")
                .errorCorrectionLevel(ErrorCorrectionLevel.H)
                .logoFlag(false)
                .format("png")
                .margin(0)
                .width(300)
                .height(300)
                .path(System.getProperty("java.io.tmpdir"));
        return qrCodeGenerator;
    }

    /**
     * 二维码内容
     */
    public QRCodeGenerator content(String content) {
        this.profile.setContent(content);
        return this;
    }

    /**
     * 图片的宽度
     */
    public QRCodeGenerator width(int width) {
        this.profile.setWidth(width);
        return this;
    }

    /**
     * 图片的高度
     */
    public QRCodeGenerator height(int height) {
        this.profile.setHeight(height);
        return this;
    }

    /**
     * 生成图片的地址（不包含图片名称）
     */
    public QRCodeGenerator path(String path) {
        this.profile.setPath(path);
        return this;
    }

    /**
     * logo文件路径
     */
    public QRCodeGenerator logoPath(String logoPath) {
        this.profile.setLogoPath(logoPath);
        return this;
    }

    /**
     * 生成图片的格式，例如:jpg，png
     */
    public QRCodeGenerator format(String format) {
        this.profile.setFormat(format);
        return this;
    }

    /**
     * 纠错级别
     */
    public QRCodeGenerator errorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        this.profile.setErrorCorrectionLevel(errorCorrectionLevel);
        return this;
    }

    /**
     * 编码格式
     */
    public QRCodeGenerator characterSet(String characterSet) {
        this.profile.setCharacterSet(characterSet);
        return this;
    }

    /**
     * 二维码边缘留白
     */
    public QRCodeGenerator margin(int margin) {
        this.profile.setWidth(margin);
        return this;
    }

    /**
     * 是否中间贴logo图，如果设置了该值为true，则必须设置logoPath
     *
     * @return
     */
    public QRCodeGenerator logoFlag(boolean logoFlag) {
        this.profile.setLogoFlag(logoFlag);
        return this;
    }

    /**
     * 生成文件
     *
     * @return 返回二维码文件
     */
    public File generate() {
        QRCode qrCode = new QRCode();
        File file = qrCode.encode(profile);
        return file;
    }

}
