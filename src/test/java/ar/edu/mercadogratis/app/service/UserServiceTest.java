package ar.edu.mercadogratis.app.service;

import ar.edu.mercadogratis.app.dao.ProductRepository;
import ar.edu.mercadogratis.app.dao.UserDaoImpl;
import ar.edu.mercadogratis.app.model.User;
import org.apache.commons.mail.EmailException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class})
public class UserServiceTest {

    @MockBean
    private UserDaoImpl userDao;
    @MockBean
    private IEmailService emailService;
    @MockBean
    private PasswordGeneratorService passwordGeneratorService;

    @TestConfiguration
    static class TestContextConfiguration {
        @Bean
        public UserService userService(UserDaoImpl userDao, IEmailService emailService,
                                                PasswordGeneratorService passwordGeneratorService) {
            return new UserService(userDao, emailService, passwordGeneratorService);
        }
    }

    @Autowired
    private UserService userService;

    @Test
    public void testAddUser() {
        String email = "validemail@mail.com";
        User user = User.builder()
                .email(email)
                .build();

        String pwd = "123add";
        when(passwordGeneratorService.generateRandom()).thenReturn(pwd);
        when(userDao.save(eq(user))).thenReturn(1L);

        Long userId = userService.addUser(user);

        assertThat(userId).isEqualTo(1L);
        assertThat(user.getPassword()).isEqualTo(pwd);
        verify(emailService).send(eq(email), anyString(), eq("Tu password es: " + pwd));
    }

    @Test
    public void testGetUser() {
        User user = User.builder()
                .build();

        when(userDao.get(eq(1L))).thenReturn(user);

        User result = userService.getUser(1L);

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void testForgetPassword() {

        String email = "validemail@mail.com";
        String pwd = "pwd";

        User user = User.builder()
                .email(email)
                .password(pwd)
                .build();

        when(userDao.getUser(eq(email))).thenReturn(user);

        userService.forgetPassword(email);

        verify(emailService).send(eq(email), eq("Bienvenido a MercadoGratis"), eq("Tu password es: " + pwd));
    }
}