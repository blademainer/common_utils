package com.kingray.email;

import com.kingray.email.vo.EmailAccount;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/8/6 0006.
 */
//@Component(value = "javaMailSender")
public class JavaMailSenderFactory extends JavaMailSenderImpl {
    /**
     * 系统邮件帐号
     */
    private String systemEmail;
    /**
     * 邮件默认抄送人
     */
    private String defaultCc;
    /**
     * 邮件默认密送人
     */
    private String defaultBcc;

    private EmailAccount emailAccount;

    private JavaMailSenderFactory(EmailAccount emailAccount) {
        this.emailAccount = emailAccount;
        this.systemEmail = emailAccount.getSystemEmail();
        this.defaultCc = emailAccount.getCc();
        this.defaultBcc = emailAccount.getBcc();

        super.setProtocol(emailAccount.getProtocol());
        super.setHost(emailAccount.getHost());
        super.setPort(emailAccount.getPort());
        super.setUsername(emailAccount.getUsername());
        super.setPassword(emailAccount.getPassword());
        Properties properties = new Properties();
        properties.setProperty("mail." + emailAccount.getProtocol() + ".auth", emailAccount.isAuth() + "");
        super.setJavaMailProperties(properties);
//        <property name="protocol" value="${email.protocol}"></property>
//        <property name="host" value="${email.host}"></property>
//        <property name="port" value="${email.port}"></property>
//        <property name="username" value="${email.username}"></property>
//        <property name="password" value="${email.password}"></property>
//        <property name="javaMailProperties">
//        <props>
//        <prop key="mail.${email.protocol}.auth">${email.auth}</prop>
//        </props>
//        </property>
    }

    public static JavaMailSenderFactory buildNewJavaMailSender(EmailAccount emailAccount) {
        return new JavaMailSenderFactory(emailAccount);
    }

    public String getSystemEmail() {
        return systemEmail;
    }

    public void setSystemEmail(String systemEmail) {
        this.systemEmail = systemEmail;
    }

    public String getDefaultCc() {
        return defaultCc;
    }

    public void setDefaultCc(String defaultCc) {
        this.defaultCc = defaultCc;
    }

    public String getDefaultBcc() {
        return defaultBcc;
    }

    public void setDefaultBcc(String defaultBcc) {
        this.defaultBcc = defaultBcc;
    }

    public EmailAccount getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(EmailAccount emailAccount) {
        this.emailAccount = emailAccount;
    }
}
