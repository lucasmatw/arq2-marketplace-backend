package ar.edu.mercadogratis.app.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.ValidationException;

@Service
@RequiredArgsConstructor
public class GmailEmailService implements IEmailService {

    @Override
    @SneakyThrows
    public void send(String receiverAddress, String subject, String message) {

        validateValidEmailAddress(receiverAddress);

        Email email = buildConfiguredEmail();
        email.addTo(receiverAddress);
        email.setSubject(subject);
        email.setMsg(message);

        email.send();
    }

    private void validateValidEmailAddress(String receiverAddress) {
        try {
            InternetAddress internetAddress = new InternetAddress(receiverAddress);
            internetAddress.validate();
        } catch (AddressException ex) {
            throw new ValidationException("Invalid receiver address: " + receiverAddress, ex);
        }
    }

    @SneakyThrows
    private Email buildConfiguredEmail() {
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
