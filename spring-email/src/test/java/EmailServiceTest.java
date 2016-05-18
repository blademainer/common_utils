import com.xiongyingqi.email.service.IEmailService;
import com.xiongyingqi.email.vo.EmailAccount;
import com.xiongyingqi.email.vo.EmailVo;
import com.xiongyingqi.email.vo.InlineImageVo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/4/25 0025.
 */
public class EmailServiceTest extends EmailBaseTest {
    @Autowired
    private IEmailService emailService;

    private EmailAccount emailAccount;

    @Before
    public void setUp() throws Exception {
        emailAccount = new EmailAccount();
        emailAccount.setNickName("blademainer");
        emailAccount.setAuth(true);
        emailAccount.setProtocol("smtps");
        emailAccount.setPort(465);
        emailAccount.setHost("smtp.gmail.com");
        emailAccount.setUsername("blademainer@gmail.com");
        emailAccount.setPassword("");
    }

    @Test
    public void testSendRichMail() throws Exception {
        EmailVo emailVo = new EmailVo();
        emailVo.setTo(new String[]{"blademainer@gmail.com"});
        emailVo.setSubject("java邮件测试~");
        emailVo.setHtml("<html><body><p>Hello Html Email</p><img src='${0}'/><img src='${1}'/><img src='${a}'/><img src='${b}'/><img src='${0}'/><img src='${a}'/></body></html>");
        InlineImageVo inlineImageVo = new InlineImageVo();
        inlineImageVo.setFile(new File(getClass().getClassLoader().getResource("pic.jpg").getFile()));
        InlineImageVo inlineImageVo2 = new InlineImageVo();
        inlineImageVo2.setFile(new File(getClass().getClassLoader().getResource("pic2.jpg").getFile()));

        InlineImageVo inlineImageVo3 = new InlineImageVo();
        inlineImageVo3.setFile(new File(getClass().getClassLoader().getResource("pic3.jpg").getFile()));
        inlineImageVo3.setContentId("a");

        InlineImageVo inlineImageVo4 = new InlineImageVo();
        inlineImageVo4.setFile(new File(getClass().getClassLoader().getResource("pic4.png").getFile()));
        inlineImageVo4.setContentId("b");

        List<InlineImageVo> inlineImageVos = new ArrayList<InlineImageVo>();
        inlineImageVos.add(inlineImageVo);
        inlineImageVos.add(inlineImageVo2);
        inlineImageVos.add(inlineImageVo3);
        inlineImageVos.add(inlineImageVo4);
        emailVo.setInlineImageVos(inlineImageVos);
        emailService.setEmailAccount(emailAccount);
        emailService.sendEmail(emailVo);
    }


    @Test
    public void testSendSimpleEmail() throws MessagingException {
        EmailVo emailVo = new EmailVo();
        emailVo.setFrom("qi");
        emailVo.setTo(new String[]{"346946073@qq.com"});
        emailVo.setSubject("java邮件测试~");
        emailVo.setHtml("呵呵~");
        emailService.setEmailAccount(emailAccount);
        emailService.sendEmail(emailVo);
    }

    public static void main(String[] args) {
    }
}
