package ar.edu.mercadoflux.app.core.usecase.moneyaccount;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface AddFundsUseCase {
    Mono<BigDecimal> addFunds(String userId, BigDecimal amount);
}
