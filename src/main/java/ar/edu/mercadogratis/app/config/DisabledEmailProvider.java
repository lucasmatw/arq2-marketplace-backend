package ar.edu.mercadogratis.app.config;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.MultiPartEmail;

public class DisabledEmailProvider implements EmailProvider {

    @Override
    public Email buildEmail() {
        return new MockEmail();
    }
    public static class MockEmail extends MultiPartEmail {
        @Override
        public String send() {
            return "Email sent!";
        }
    }
}
