package ar.edu.mercadogratis.app.config;

import org.apache.commons.mail.Email;

public interface EmailProvider {
    Email buildEmail();
}
