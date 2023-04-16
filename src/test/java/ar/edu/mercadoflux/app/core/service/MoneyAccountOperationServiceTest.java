package ar.edu.mercadoflux.app.core.service;

import ar.edu.mercadoflux.app.core.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoneyAccountOperationServiceTest {

    @InjectMocks
    private MoneyAccountOperationService moneyAccountOperationService;

    @Mock
    private MoneyAccountService moneyAccountService;
    @Mock
    private UserService userService;

    @Test
    void addFundsMustAddFundsToCurrentBalance() {

        // given
        String userId = "userId";

        User userMock = mock(User.class);

        BigDecimal fundsToAdd = BigDecimal.valueOf(100);
        BigDecimal resultingBalance = BigDecimal.valueOf(300);


        when(userService.getUser(eq(userId))).thenReturn(Mono.just(userMock));
        when(moneyAccountService.creditAmount(eq(userMock), eq(fundsToAdd))).thenReturn(Mono.just(resultingBalance));

        // when
        Mono<BigDecimal> result = moneyAccountOperationService.addFunds(userId, fundsToAdd);

        // then
        assertThat(result.block()).isEqualTo(resultingBalance);
    }
}