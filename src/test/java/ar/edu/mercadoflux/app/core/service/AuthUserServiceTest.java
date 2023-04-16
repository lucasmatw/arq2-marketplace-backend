package ar.edu.mercadoflux.app.core.service;

import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.dto.AuthUser;
import ar.edu.mercadoflux.app.core.exception.InvalidUserOrPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthUserServiceTest {
    @InjectMocks
    private AuthUserService authUserService;
    @Mock
    private UserService userService;

    @Test
    @DisplayName("should login user when credentials are correct")
    void testLogin() {
        // given
        String userMail = "user@mail";
        String userPwd = "pwd";

        AuthUser authUser = new AuthUser(userMail, userPwd);

        User userMock = mock(User.class);
        when(userMock.getPassword()).thenReturn(userPwd);

        when(userService.getUserForMail(userMail)).thenReturn(Mono.just(userMock));

        // when
        Mono<User> login = authUserService.login(authUser);

        // then
        assertThat(login.block()).isEqualTo(userMock);
    }

    @Test
    @DisplayName("should return empty Mono when credentials are not correct")
    void testLoginInvalidPwd() {
        // given
        String userMail = "user@mail";
        String loginPwd = "incorrect pwd";

        AuthUser authUser = new AuthUser(userMail, loginPwd);

        User userMock = mock(User.class);
        when(userMock.getPassword()).thenReturn("pwd");

        when(userService.getUserForMail(userMail)).thenReturn(Mono.just(userMock));

        // when
        Mono<User> login = authUserService.login(authUser);

        // then
        assertThatThrownBy(login::block).isInstanceOf(InvalidUserOrPasswordException.class);
    }
}