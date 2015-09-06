package helpers;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Play;

/**
 * Created by dastko on 9/6/15.
 */
public class MailHelper {

    final static Logger logger = LoggerFactory.getLogger(MailHelper.class);
    public static void send(String email, String message) {

        try {
            HtmlEmail mail = new HtmlEmail();
            mail.setSubject("bitBlog Welcome");
            mail.setFrom("bitBlog <bitblog@gmail.com>");
            mail.addTo("Contact <bitblog@gmail.com>");
            mail.addTo(email);
            mail.setMsg(message);
            mail.setHtmlMsg(String
                    .format("<html><body><strong> %s </strong> <p> %s </p> <p> %s </p> </body></html>",
                            "Thanks for signing up to bitBlog!",
                            "Please confirm your Email adress :).", message));
            mail.setHostName("smtp.gmail.com");
            mail.setStartTLSEnabled(true);
            mail.setSSLOnConnect(true);
            mail.setAuthenticator(new DefaultAuthenticator(
                    Play.application().configuration().getString("EMAIL_USERNAME_ENV"),
                    Play.application().configuration().getString("EMAIL_PASSWORD_ENV")
            ));
            mail.send();
        } catch (Exception e) {
            logger.warn("Email error" + e);
        }
    }
}
