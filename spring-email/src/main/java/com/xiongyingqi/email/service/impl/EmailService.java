package com.xiongyingqi.email.service.impl;

import com.xiongyingqi.email.JavaMailSenderFactory;
import com.xiongyingqi.email.service.IEmailService;
import com.xiongyingqi.email.vo.AttachmentVo;
import com.xiongyingqi.email.vo.EmailAccount;
import com.xiongyingqi.email.vo.EmailVo;
import com.xiongyingqi.email.vo.InlineImageVo;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/4/25 0025.
 */
@Service
public class EmailService implements IEmailService {
    private JavaMailSenderFactory javaMailSenderFactory;

    private EmailAccount emailAccount;

//    /**
//     * 系统邮件帐号
//     */
//    @Value("${email.systemEmail}")
//    private String systemEmail;
//    /**
//     * 邮件默认抄送人
//     */
//    @Value("${email.cc}")
//    private String defaultCc;
//    /**
//     * 邮件默认密送人
//     */
//    @Value("${email.bcc}")
//    private String defaultBcc;


    /**
     * 查找html内img的内容
     */
    public static final Pattern PATTERN_FIND_TAG_AND_INDEX = Pattern.compile("<\\w+\\s+\\w+\\s*=\\s*(\\'\\s*\\$\\s*\\{\\s*\\d+\\s*\\}\\s*\\'\\s*|\\\"\\s*\\$\\s*\\{\\s*\\w+\\s*\\}\\s*\\\"\\s*){1}?\\s*/{0,1}>");
    /**
     * 查找${0}的类似内容
     */
    public static final Pattern PATTERN_DOLLAR_VARIABLE_INDEX = Pattern.compile("\\s*\\$\\{\\s*\\d+\\s*\\}\\s*");


    /**
     * 查找html内img的内容
     */
    public static final Pattern PATTERN_FIND_TAG = Pattern.compile("<\\w+\\s+src\\s*=\\s*(\\'\\s*\\$\\{\\s*\\w+\\s*\\}\\s*\\'\\s*|\\\"\\s*\\$*\\{\\s*\\w+\\s*\\}\\s*\\\"\\s*){1}?\\s*/{0,1}>");
    /**
     * 查找${a}的类似内容
     */
    public static final Pattern PATTERN_DOLLAR_VARIABLE_SELF_DEFINE = Pattern.compile("\\s*\\$\\{\\s*\\w+\\s*\\}\\s*");

    public EmailService(EmailAccount emailAccount) {
        this.javaMailSenderFactory = JavaMailSenderFactory.buildNewJavaMailSender(emailAccount);
    }

    public EmailService() {
    }

    public EmailAccount getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(EmailAccount emailAccount) {
        this.emailAccount = emailAccount;
        this.javaMailSenderFactory = JavaMailSenderFactory.buildNewJavaMailSender(emailAccount);
    }

    /**
     * 发送邮件
     *
     * @param emailVo
     */
    @Override
    public void sendEmail(EmailVo emailVo) throws MessagingException {
        Assert.notNull(emailVo.getTo(), "接收人不能为空");

        MimeMessage msg = javaMailSenderFactory.createMimeMessage();

        MimeMessageHelper msgHelper = null;
        if ((emailVo.getInlineImageVos() != null && emailVo.getInlineImageVos().size() > 0) || (emailVo.getAttachmentVos() != null && emailVo.getAttachmentVos().size() > 0)) {
            msgHelper = new MimeMessageHelper(msg, true, "utf-8");
        } else {
            msgHelper = new MimeMessageHelper(msg, "utf-8");
        }


        if (emailVo.getFrom() == null || "".equals(emailVo.getFrom().trim())) {
            emailVo.setFrom(javaMailSenderFactory.getSystemEmail());
        }
        if ((emailVo.getCc() == null || "".equals(emailVo.getCc().length == 0)) && javaMailSenderFactory.getDefaultCc() != null && !javaMailSenderFactory.getDefaultCc().equals("")) {
            emailVo.setCc(javaMailSenderFactory.getDefaultCc().split(","));
        }
        if ((emailVo.getBcc() == null || "".equals(emailVo.getBcc().length == 0)) && javaMailSenderFactory.getDefaultBcc() != null && !javaMailSenderFactory.getDefaultBcc().equals("")) {
            emailVo.setBcc(javaMailSenderFactory.getDefaultBcc().split(","));
        }


        if (emailVo.getMessageDate() == null) {
            emailVo.setMessageDate(new Date());
        }
        if (emailVo.getCc() != null) {
            msgHelper.setCc(emailVo.getCc());// 抄送
        }
        if (emailVo.getBcc() != null) {
            msgHelper.setBcc(emailVo.getBcc());// 密送
        }
        if (emailVo.getSubject() != null) {
            msgHelper.setSubject(emailVo.getSubject());
        }


        handlerAttachments(emailVo, msgHelper);

        String from = null;
        if (emailVo.getFrom() != null && !"".equals(emailVo.getFrom())) {
            from = emailVo.getFrom();
        } else {
            if (javaMailSenderFactory.getEmailAccount().getFrom() != null && !"".equals(javaMailSenderFactory.getEmailAccount().getFrom())) {
                from = javaMailSenderFactory.getEmailAccount().getFrom();
            } else {
                from = javaMailSenderFactory.getEmailAccount().getUsername();
            }
        }

        try {
            if (javaMailSenderFactory.getEmailAccount().getNickName() != null) {
                msgHelper.setFrom(from, javaMailSenderFactory.getEmailAccount().getNickName());
            } else {
                msgHelper.setFrom(from);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            msgHelper.setFrom(from);
        }


        msgHelper.setTo(emailVo.getTo());// 接收人
        Collection<Inline> inlines = handlerInlineImages(emailVo, msgHelper);

        if (emailVo.getHtml() != null) {
            msgHelper.setText(emailVo.getHtml(), emailVo.isHtml());

            if (inlines != null) {// 添加inline
                for (Inline inline : inlines) {
                    msgHelper.addInline(inline.getContentId(), inline.getFile());
                }
            }
        }
        javaMailSenderFactory.send(msg);
    }

    /**
     * 处理内嵌对象
     *
     * @param emailVo
     * @param msgHelper
     * @throws javax.mail.MessagingException
     */
    private Collection<Inline> handlerInlineImages(EmailVo emailVo, MimeMessageHelper msgHelper) throws MessagingException {
        Collection<Inline> inlines = null;
        if (emailVo.getInlineImageVos() != null && emailVo.getInlineImageVos().size() > 0) {
            inlines = new HashSet<Inline>();
            String html = emailVo.getHtml();
            if (html == null || html.trim().equals("")) {// 如果内容空或者不是 html则直接跳过
                return inlines;
            }

            //  ------------------------------------------- 查找带数字的标记 ${数字} -------------------------------------------
            Matcher matcherIndexTag = PATTERN_FIND_TAG_AND_INDEX.matcher(html);

            int contentIdIndex = 0;
            while (matcherIndexTag.find()) {

                String htmlTag = matcherIndexTag.group(); // 查找到<img src="${1}" /> 的内容
                Matcher matcherDollar = PATTERN_DOLLAR_VARIABLE_INDEX.matcher(htmlTag);
                if (matcherDollar.find()) {

                    String dollarFind = matcherDollar.group();// 查找到${1}内容
                    String indexStr = dollarFind.replace("$", "").replace("{", "").replace("}", "").trim();// 移除外面的$符号和大括号
                    int index = Integer.parseInt(indexStr);
                    try {
                        InlineImageVo inlineImageVo = emailVo.getInlineImageVos().get(index);
                        File file = inlineImageVo.getFile();

                        String contentId = "file" + contentIdIndex++; // 生成contentId
//                        msgHelper.addInline(contentId, file);// 将附件内容传送给MimeMessageHelper

                        Inline inline = new Inline();
                        inline.setFile(file);
                        inline.setContentId(contentId);

                        inlines.add(inline);

                        htmlTag = matcherDollar.replaceAll("cid:" + contentId);
//                        System.out.println("indexStr ====== " + indexStr);
//                        System.out.println("htmlTag ====== " + htmlTag);

                        html = matcherIndexTag.replaceFirst(htmlTag);
                        matcherIndexTag = PATTERN_FIND_TAG_AND_INDEX.matcher(html);

//                        System.out.println("html ====== " + html);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            //  ------------------------------------------- 查找带字母变量的标记 ${variable} -------------------------------------------
            Matcher matcherTag = PATTERN_FIND_TAG.matcher(html);
            while (matcherTag.find()) {
                String htmlTag = matcherTag.group(); // 查找到<img src="${1}" /> 的内容
                Matcher matcherDollar = PATTERN_DOLLAR_VARIABLE_SELF_DEFINE.matcher(htmlTag);
                if (matcherDollar.find()) {
                    String dollarFind = matcherDollar.group();// 查找到${1}内容
                    String dollarVariable = dollarFind.replace("$", "").replace("{", "").replace("}", "").trim();// 移除外面的$符号和大括号
                    try {
                        InlineImageVo inlineImageVo = null;
                        for (InlineImageVo imageVo : emailVo.getInlineImageVos()) {
                            if (imageVo.getContentId() != null && imageVo.getContentId().equals(dollarVariable)) {
                                inlineImageVo = imageVo;
                            }
                        }

                        if (inlineImageVo == null) {
                            continue;
                        }
                        File file = inlineImageVo.getFile();

//                        String contentId = "file" + contentIdIndex++; // 生成contentId
//                        msgHelper.addInline(contentId, file);// 将附件内容传送给MimeMessageHelper

                        Inline inline = new Inline();
                        inline.setFile(file);
                        inline.setContentId(inlineImageVo.getContentId());

                        inlines.add(inline);

                        htmlTag = matcherDollar.replaceAll("cid:" + inlineImageVo.getContentId());
//                        System.out.println("htmlTag ====== " + htmlTag);

                        html = matcherTag.replaceFirst(htmlTag);
                        matcherTag = PATTERN_FIND_TAG.matcher(html);

//                        System.out.println("html ====== " + html);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }


            emailVo.setHtml(html);

            if (inlines != null && inlines.size() > 0) {
                emailVo.setHtml(true);
            }
        }
        return inlines;
    }

    /**
     * 处理附件
     *
     * @param emailVo
     * @param msgHelper
     * @throws javax.mail.MessagingException
     */
    private void handlerAttachments(EmailVo emailVo, MimeMessageHelper msgHelper) throws MessagingException {
        if (emailVo.getAttachmentVos() != null) {// 检查附件
            for (AttachmentVo attachmentVo : emailVo.getAttachmentVos()) {
                File attachment = attachmentVo.getAttachment();


                if (attachment == null) {
                    InputStream inputStream = attachmentVo.getAttachmentInputStream();

                    if (attachmentVo.getAttachmentName() == null) {
                        attachmentVo.setAttachmentName(new Date().toString());
                    }
                    InputStreamSource inputStreamSource = new InputStreamResource(inputStream);
                    msgHelper.addAttachment(attachmentVo.getAttachmentName(), inputStreamSource);
                } else {
                    if (attachmentVo.getAttachmentName() == null) {
                        attachmentVo.setAttachmentName(attachment.getName());
                    }

                    try {
                        msgHelper.addAttachment(MimeUtility.encodeWord(attachmentVo.getAttachmentName()), attachment);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class Inline {
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

}
