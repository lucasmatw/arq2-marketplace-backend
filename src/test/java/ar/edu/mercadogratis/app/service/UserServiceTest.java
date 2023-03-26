package ar.edu.mercadogratis.app.service;

import ar.edu.mercadogratis.app.dao.UserRepository;
import ar.edu.mercadogratis.app.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class})
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private EmailService emailService;

    @TestConfiguration
    static class TestContextConfiguration {
        @Bean
        public UserService userService(UserRepository userRepository,
                                       MoneyAccountService moneyAccountService) {
            return new UserService(userRepository, moneyAccountService);
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private MoneyAccountService moneyAccountService;

    @Test
    public void testAddUser() {
        String email = "validemail@mail.com";
        String pwd = "123add";

        User user = User.builder()
                .email(email)
                .password(pwd)
                .build();


        User savedUser = user.toBuilder().
                build();
        savedUser.setId(1L);
        when(userRepository.save(eq(user))).thenReturn(savedUser);

        Long userId = userService.createUser(user);

        assertThat(userId).isEqualTo(1L);
        assertThat(user.getPassword()).isEqualTo(pwd);
    }

    @Test
    public void testGetUser() {
        User user = User.builder()
                .build();

        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(user));

        User result = userService.getUser(1L);

        assertThat(result).isEqualTo(user);
    }
}