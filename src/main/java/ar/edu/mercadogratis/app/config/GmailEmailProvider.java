package ar.edu.mercadogratis.app.config;

import lombok.SneakyThrows;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.MultiPartEmail;

public class GmailEmailProvider implements EmailProvider {

    @SneakyThrows
    @Override
    public Email buildEmail() {
        MultiPartEmail multiPartEmail = new MultiPartEmail();
        multiPartEmail.setHostName("smtp.gmail.com");
        multiPartEmail.setStartTLSEnabled(true);
        multiPartEmail.setSmtpPort(465);
        multiPartEmail.setSSLOnConnect(true);
        multiPartEmail.setAuthentication("mercadogratisunq@gmail.com", "MercadoGratis2021");
        multiPartEmail.setFrom("mercadogratisunq@gmail.com");

        return multiPartEmail;
    }
}
