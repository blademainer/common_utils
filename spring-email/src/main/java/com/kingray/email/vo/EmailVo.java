package com.kingray.email.vo;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/4/25 0025.
 */
public class EmailVo  {
    private String from;
    private String[] to;
    private String subject;
    private String[] cc;
    private String[] bcc;
    private String html;
    private boolean isHtml;
    private Date messageDate;
    private List<AttachmentVo> attachmentVos;
    private List<InlineImageVo> inlineImageVos;


    public String getFrom() {
        return from;
    }

    /**
     * 发件人<br>
     * 如果不设置即为service.properties中配置的email.systemEmail属性值
     */
    public void setFrom(String from) {
        this.from = from;
    }


    public String[] getTo() {
        return to;
    }

    /**
     * 收件人，可以是多个
     * @param to
     */
    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    /**
     * 抄送
     * @param cc
     */
    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    /**
     * 密送
     * @param bcc
     */
    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public String getHtml() {
        return html;
    }

    /**
     * 消息内容，如果isHtml设置为true，则html内容应当为html内容，否则则为纯文本
     * @param html
     */
    public void setHtml(String html) {
        this.html = html;
    }

    public boolean isHtml() {
        return isHtml;
    }

    /**
     * 设置邮件内容是否为html
     * @param isHtml
     */
    public void setHtml(boolean isHtml) {
        this.isHtml = isHtml;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    /**
     * 设置邮件日期，如果不设置则为系统时间
     * @param messageDate
     */
    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    public Collection<AttachmentVo> getAttachmentVos() {
        return attachmentVos;
    }

    /**
     * 设置附件
     * @param attachmentVos
     */
    public void setAttachmentVos(List<AttachmentVo> attachmentVos) {
        this.attachmentVos = attachmentVos;
    }

    /**
     * 设置内嵌对象（图片或者视频等）
     * 邮件内的内容必须是以下形式
     * <pre>
     *     &lt;html&gt;&lt;head&gt;&lt;/head&gt;&lt;body&gt;&lt;h1&gt;hello!!spring image html mail&lt;/h1&gt;&lt;img src="${ 0 }" /&gt;&lt;img src="${ 1 }"&gt;&lt;img src="${ 1 }"&gt;&lt;/body&gt;&lt;/html&gt;
     * </pre>
     * 此种方法contentId必须为空，系统会自动读取参数${0}并将inlineImageVos中的第一个图片设置给该参数，${1}并将inlineImageVos中的第二个图片<br></br>
     * 或者：<br>
     * <pre>
     *     &lt;html&gt;&lt;head&gt;&lt;/head&gt;&lt;body&gt;&lt;h1&gt;hello!!spring image html mail&lt;/h1&gt;&lt;img src="${image_first}" /&gt;&lt;img src="${image_second}"&gt;&lt;img src="${image_first}"&gt;&lt;/body&gt;&lt;/html&gt;
     * </pre>
     *  这种方式系统会读取InlineImageVo中的contentId并匹配是否与"image_first"相等，如果找到相等值，则设置InlineImageVo中的file替代
     *
     * @return
     */
    public List<InlineImageVo> getInlineImageVos() {
        return inlineImageVos;
    }

    public void setInlineImageVos(List<InlineImageVo> inlineImageVos) {
        this.inlineImageVos = inlineImageVos;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
