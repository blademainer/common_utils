package com.kingray.email.service;

import com.kingray.email.vo.EmailAccount;
import com.kingray.email.vo.EmailVo;

import javax.mail.MessagingException;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/4/25 0025.
 */
public interface IEmailService {
    /**
     * 发送邮件
     * @param emailVo
     */
    public void sendEmail(EmailVo emailVo) throws MessagingException;

    public void setEmailAccount(EmailAccount emailAccount);
}
