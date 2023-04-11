package ar.edu.mercadoflux.app.core.repository;

import ar.edu.mercadoflux.app.core.domain.MoneyAccount;
import ar.edu.mercadoflux.app.core.domain.User;
import reactor.core.publisher.Mono;

public interface MoneyAccountRepository  {
    Mono<MoneyAccount> getByUser(User user);
    Mono<MoneyAccount> save(MoneyAccount moneyAccount);
    Mono<MoneyAccount> update(MoneyAccount moneyAccount);
}
