package com.xiongyingqi.qrcode;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.Serializable;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/10/17 0017.
 */
public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 二维码内容
     */
    private String content;
    /**
     * 图片的宽度
     */
    private int width;
    /**
     * 图片的高度
     */
    private int height;
    /**
     * 生成图片的地址（不包含图片名称）
     */
    private String path;
    /**
     * logo图地址
     */
    private String logoPath;
    /**
     * 生成图片的格式
     */
    private String format;
    /**
     * 纠错级别
     */
    private ErrorCorrectionLevel errorCorrectionLevel;
    /**
     * 编码格式
     */
    private String characterSet;
    /**
     * 二维码边缘留白
     */
    private int margin;
    /**
     * 是否中间贴图
     *
     * @return
     */
    private boolean logoFlag;

    /**
     * getter and setter
     */

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public ErrorCorrectionLevel getErrorCorrectionLevel() {
        return errorCorrectionLevel;
    }

    public void setErrorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        this.errorCorrectionLevel = errorCorrectionLevel;
    }

    public String getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(String characterSet) {
        this.characterSet = characterSet;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public boolean isLogoFlag() {
        return logoFlag;
    }

    public void setLogoFlag(boolean logoFlag) {
        this.logoFlag = logoFlag;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

}
