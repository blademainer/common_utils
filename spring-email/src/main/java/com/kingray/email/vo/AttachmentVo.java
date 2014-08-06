package com.kingray.email.vo;

import java.io.File;
import java.io.InputStream;

/**
 * 邮件附件<br>
 * attachment和attachmentInputStream任选，如果attachment不为空则选用attachment，否则判断attachmentInputStream<br>
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/4/25 0025.
 */
public class AttachmentVo {
    private File attachment;
    private InputStream attachmentInputStream;
    private String attachmentName;

    public File getAttachment() {
        return attachment;
    }

    public void setAttachment(File attachment) {
        this.attachment = attachment;
    }

    public InputStream getAttachmentInputStream() {
        return attachmentInputStream;
    }

    public void setAttachmentInputStream(InputStream attachmentInputStream) {
        this.attachmentInputStream = attachmentInputStream;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }
}
