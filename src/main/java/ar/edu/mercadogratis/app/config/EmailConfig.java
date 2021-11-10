package ar.edu.mercadogratis.app.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Bean
    @ConditionalOnProperty(name = "mailing.enabled", havingValue = "true")
    public EmailProvider enabledEmailConfiguration() {
        return new GmailEmailProvider();
    }

    @Bean
    @ConditionalOnProperty(name = "mailing.enabled", havingValue = "false")
    public EmailProvider disabledEmailConfiguration() {
        return new DisabledEmailProvider();
    }
}
