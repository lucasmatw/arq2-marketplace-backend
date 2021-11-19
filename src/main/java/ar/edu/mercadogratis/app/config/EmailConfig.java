package ar.edu.mercadogratis.app.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Bean
    @ConditionalOnProperty(name = "mailing.provider", havingValue = "mailgun")
    public EmailProvider mailgunEmailConfiguration() {
        return new MailgunEmailProvider();
    }

    @Bean
    @ConditionalOnProperty(name = "mailing.provider", havingValue = "gmail")
    public EmailProvider gmailEmailConfiguration() {
        return new GmailEmailProvider();
    }

    @Bean
    @ConditionalOnProperty(name = "mailing.provider", havingValue = "disabled")
    public EmailProvider disabledEmailConfiguration() {
        return new DisabledEmailProvider();
    }
}
