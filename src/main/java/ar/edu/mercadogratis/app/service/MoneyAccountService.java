package ar.edu.mercadogratis.app.service;

import ar.edu.mercadogratis.app.dao.MoneyAccountRepository;
import ar.edu.mercadogratis.app.exceptions.ValidationException;
import ar.edu.mercadogratis.app.model.MoneyAccount;
import ar.edu.mercadogratis.app.model.User;
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
                .map(MoneyAccount::getBalance);
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
                        return Mono.error(new ValidationException("insufficient_funds", "No funds available"));
                    }
                    return Mono.just(account);
                }).flatMap(moneyAccount -> {
                    moneyAccount.subtractFromBalance(amount);
                    return moneyAccountRepository.save(moneyAccount);
                }).map(MoneyAccount::getBalance);
    }

    private boolean insufficientFunds(MoneyAccount moneyAccount, BigDecimal amount) {
        return moneyAccount.getBalance().compareTo(amount) < 0;
    }

    public Mono<BigDecimal> creditAmount(User user, BigDecimal amount) {
        return moneyAccountRepository.getByUser(user)
                .flatMap(account -> {
                    account.addToBalance(amount);
                    return Mono.just(account);
                })
                .doOnNext(moneyAccountRepository::save)
                .map(MoneyAccount::getBalance);
    }
}
