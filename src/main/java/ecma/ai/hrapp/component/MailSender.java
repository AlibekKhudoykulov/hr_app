package ecma.ai.hrapp.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Component
public class MailSender {

    @Autowired
    JavaMailSender mailSender;

    public boolean send(String to, String text) throws MessagingException {

        String from = "Alibek@gmail.com";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setSubject("Information");
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setText(text);
        mailSender.send(mimeMessage);
        return true;
    }


    public boolean mailTextAddStaff(String email, String code, String pass) throws MessagingException {
        String link = "http://localhost:80/api/user/verifyEmail?email=" + email + "&code=" + code;

        String text = "<a href=\"" + link + "\">Emailni tasdiqlash</a>\n" +
                "<br>\n" +
                "<p>Parolingiz: " + pass + "</p>";

        return send(email, text);
    }
    public boolean mailTextAddTask(String email, String name) throws MessagingException {
        String text = "You have been given a task called " + name + "." + "<br>" + "<a href=\"\" style=\"padding: 10px 15px; background-color: darkslateblue; color: white; text-decoration: none; border-radius: 4px; margin: 10px; display: flex; max-width: 120px;\">View task</a>\n" +
                "<br>\n";
        return send(email, text);
    }


//<a href="http://localhost:80/api/user/verifyEmail?email=official.sah.biz@gmail.com&code02defc1d-481c-4352-aaa5-4d1980d70d6f">Emailni tasdiqlash</a>
//<br>
//<p>Parolingiz: P7QSHpLg</p>
}
