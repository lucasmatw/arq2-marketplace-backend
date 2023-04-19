package ar.edu.mercadoflux.app.core.service;

import ar.edu.mercadoflux.app.core.exception.InsufficientFundsException;
import ar.edu.mercadoflux.app.core.exception.MoneyAccountNotFoundException;
import ar.edu.mercadoflux.app.core.repository.MoneyAccountRepository;
import ar.edu.mercadoflux.app.core.domain.MoneyAccount;
import ar.edu.mercadoflux.app.core.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class MoneyAccountService {

    private final MoneyAccountRepository moneyAccountRepository;

    public Mono<BigDecimal> getFunds(User user) {
        return moneyAccountRepository.getByUser(user)
                .map(MoneyAccount::getBalance)
                .switchIfEmpty(Mono.error(new MoneyAccountNotFoundException()));
    }

    public Mono<MoneyAccount> registerAccount(User user) {
        MoneyAccount account = MoneyAccount.builder()
                .balance(BigDecimal.ZERO)
                .user(user)
                .build();

        return moneyAccountRepository.save(account);
    }

    public Mono<BigDecimal> debitAmount(User user, BigDecimal amount) {
        return moneyAccountRepository.getByUser(user)
                .flatMap(account -> {
                    if (insufficientFunds(account, amount)) {
                        return Mono.error(new InsufficientFundsException());
                    }
                    return Mono.just(account);
                })
                .map(account -> account.subtractFromBalance(amount))
                .flatMap(moneyAccountRepository::update)
                .map(MoneyAccount::getBalance)
                .switchIfEmpty(Mono.error(new MoneyAccountNotFoundException()));
    }

    public Mono<BigDecimal> creditAmount(User user, BigDecimal amount) {
        return moneyAccountRepository.getByUser(user)
                .map(account -> account.addToBalance(amount))
                .flatMap(moneyAccountRepository::update)
                .map(MoneyAccount::getBalance)
                .switchIfEmpty(Mono.error(new MoneyAccountNotFoundException()));
    }

    private boolean insufficientFunds(MoneyAccount moneyAccount, BigDecimal amount) {
        return moneyAccount.getBalance().compareTo(amount) < 0;
    }
}
