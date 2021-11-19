package ar.edu.mercadogratis.app.service;

import ar.edu.mercadogratis.app.config.EmailProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.mail.Email;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.ValidationException;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final EmailProvider emailConfiguration;

    @Override
    @SneakyThrows
    public void send(String receiverAddress, String subject, String message) {

        validateValidEmailAddress(receiverAddress);

        Email email = emailConfiguration.buildEmail();
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
}
