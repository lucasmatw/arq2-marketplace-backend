package ar.edu.mercadogratis.app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith({SpringExtension.class})
class PasswordGeneratorServiceTest {

    @TestConfiguration
    static class TestContextConfiguration {
        @Bean
        public PasswordGeneratorService passwordGeneratorService() {
            return new PasswordGeneratorService();
        }
    }

    @Autowired
    private PasswordGeneratorService passwordGeneratorService;

    @Test
    public void testGenerate() {
        String password = passwordGeneratorService.generateRandom();
        assertThat(password).hasSize(15);
    }
}