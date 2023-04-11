package ar.edu.mercadoflux.app.core.usecase.moneyaccount;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface GetFundsUseCase {
    Mono<BigDecimal> getFunds(String userId);
}
