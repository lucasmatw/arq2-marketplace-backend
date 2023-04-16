package ar.edu.mercadoflux.app.core.service;

import ar.edu.mercadoflux.app.core.domain.MoneyAccount;
import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.domain.UserStatus;
import ar.edu.mercadoflux.app.core.domain.UserType;
import ar.edu.mercadoflux.app.core.dto.RegisterUser;
import ar.edu.mercadoflux.app.core.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private MoneyAccountService moneyAccountService;

    @Test
    @DisplayName("Register user creating an user and a money account")
    void registerUser() {
        // given
        RegisterUser registerUser = RegisterUser.builder()
                .name("name")
                .email("email")
                .password("password")
                .lastName("lastName")
                .cuit("cuit")
                .build();

        User userToSave = User.builder()
                .name("name")
                .email("email")
                .password("password")
                .lastName("lastName")
                .cuit("cuit")
                .status(UserStatus.ACTIVE)
                .type(UserType.CUSTOMER)
                .build();

        MoneyAccount moneyAccount = MoneyAccount.builder()
                .user(userToSave)
                .balance(BigDecimal.ZERO)
                .build();

        when(moneyAccountService.registerAccount(eq(userToSave))).thenReturn(Mono.just(moneyAccount));
        when(userRepository.findByEmail(eq("email"))).thenReturn(Mono.empty());
        when(userRepository.save(eq(userToSave))).thenReturn(Mono.just(userToSave));

        // when
        User user = userService.registerUser(registerUser).block();

        // then
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getEmail()).isEqualTo("email");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getLastName()).isEqualTo("lastName");
        assertThat(user.getCuit()).isEqualTo("cuit");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);

        verify(userRepository).save(eq(userToSave));
        verify(moneyAccountService).registerAccount(eq(userToSave));
    }

    @Test
    @DisplayName("Register a seller creating an user and a money account")
    void registerSellerUser() {

        // given
        RegisterUser registerUser = RegisterUser.builder()
                .name("name")
                .email("email")
                .password("password")
                .lastName("lastName")
                .cuit("cuit")
                .build();

        User userToSave = User.builder()
                .name("name")
                .email("email")
                .password("password")
                .lastName("lastName")
                .cuit("cuit")
                .status(UserStatus.ACTIVE)
                .type(UserType.SELLER)
                .build();

        MoneyAccount moneyAccount = MoneyAccount.builder()
                .user(userToSave)
                .balance(BigDecimal.ZERO)
                .build();

        when(moneyAccountService.registerAccount(eq(userToSave))).thenReturn(Mono.just(moneyAccount));
        when(userRepository.findByEmail(eq("email"))).thenReturn(Mono.empty());
        when(userRepository.save(eq(userToSave))).thenReturn(Mono.just(userToSave));

        // when
        User user = userService.registerSellerUser(registerUser).block();

        // then
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getEmail()).isEqualTo("email");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getLastName()).isEqualTo("lastName");
        assertThat(user.getCuit()).isEqualTo("cuit");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);

        verify(userRepository).save(eq(userToSave));
        verify(moneyAccountService).registerAccount(eq(userToSave));
    }
}