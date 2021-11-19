package ar.edu.mercadogratis.app.config;

import lombok.SneakyThrows;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.beans.factory.annotation.Value;

public class MailgunEmailProvider implements EmailProvider {

    @Value("${mailgun.smtp.port}")
    private int smtpPort;

    @Value("${mailgun.smtp.server}")
    private String smtpServer;

    @Value("${mailgun.smtp.login}")
    private String login;

    @Value("${mailgun.smtp.password}")
    private String password;

    @SneakyThrows
    @Override
    public Email buildEmail() {
        MultiPartEmail multiPartEmail = new MultiPartEmail();
        multiPartEmail.setHostName(smtpServer);
        multiPartEmail.setSmtpPort(smtpPort);
        multiPartEmail.setAuthenticator(new DefaultAuthenticator(login, password));
        multiPartEmail.setFrom(login);
        multiPartEmail.setSSLOnConnect(true);
        multiPartEmail.setStartTLSEnabled(true);

        return multiPartEmail;
    }
}
