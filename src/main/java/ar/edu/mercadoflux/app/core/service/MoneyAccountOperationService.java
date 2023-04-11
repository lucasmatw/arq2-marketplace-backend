package ar.edu.mercadoflux.app.core.service;

import ar.edu.mercadoflux.app.core.usecase.moneyaccount.AddFundsUseCase;
import ar.edu.mercadoflux.app.core.usecase.moneyaccount.GetFundsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MoneyAccountOperationService implements AddFundsUseCase, GetFundsUseCase {

    private final MoneyAccountService moneyAccountService;
    private final UserService userService;

    @Override
    public Mono<BigDecimal> addFunds(String userId, BigDecimal amount) {
        return userService.getUser(userId)
                .flatMap(user -> moneyAccountService.creditAmount(user, amount));
    }

    @Override
    public Mono<BigDecimal> getFunds(String userId) {
        return userService.getUser(userId)
                .flatMap(moneyAccountService::getFunds);
    }
}
