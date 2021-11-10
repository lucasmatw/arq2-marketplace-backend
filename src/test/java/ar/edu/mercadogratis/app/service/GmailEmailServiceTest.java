package ar.edu.mercadogratis.app.service;

import ar.edu.mercadogratis.app.config.EmailProvider;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class})
public class GmailEmailServiceTest {

    @TestConfiguration
    static class TestContextConfiguration {
        @Bean
        public GmailEmailService productService(EmailProvider emailProvider) {
            return new GmailEmailService(emailProvider);
        }
    }

    @Autowired
    private GmailEmailService gmailEmailService;

    @MockBean
    private EmailProvider emailProvider;

    @Test
    public void testSend() throws EmailException {
        String receiver = "receiver@mail.com";
        String subject = "any subject";
        String message = "any message";

        Email email = mock(Email.class);
        when(emailProvider.buildEmail()).thenReturn(email);

        gmailEmailService.send(receiver, subject, message);

        verify(email).addTo(eq(receiver));
        verify(email).setMsg(eq(message));
        verify(email).setSubject(eq(subject));
        verify(email).send();
    }
}