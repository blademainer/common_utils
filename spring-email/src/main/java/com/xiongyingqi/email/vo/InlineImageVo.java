package com.xiongyingqi.email.vo;

import java.io.File;

/**
 * 内嵌图片<br>
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/4/25 0025.
 */
public class InlineImageVo {
    private String contentId;
    private File file;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
