package ar.edu.mercadoflux.app.ports.output.persistence.adapter;

import ar.edu.mercadoflux.app.core.domain.MoneyAccount;
import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.repository.MoneyAccountRepository;
import ar.edu.mercadoflux.app.ports.output.persistence.entities.MoneyAccountDocument;
import ar.edu.mercadoflux.app.ports.output.persistence.repository.mongo.ReactiveMoneyAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MoneyAccountRepositoryMongoAdapter implements MoneyAccountRepository {

    private final ReactiveMoneyAccountRepository moneyAccountRepository;

    @Override
    public Mono<MoneyAccount> getByUser(User user) {
        return moneyAccountRepository.findByUserId(user.getId())
                .map(moneyAccountDocument -> moneyAccountDocument.toMoneyAccount(user));
    }

    @Override
    public Mono<MoneyAccount> update(MoneyAccount moneyAccount) {
        return moneyAccountRepository.findByUserId(moneyAccount.getUser().getId())
                .map(ma -> MoneyAccountDocument.fromMoneyAccount(ma.getId(), moneyAccount))
                .flatMap(moneyAccountRepository::save)
                .map(moneyAccountDocument -> moneyAccountDocument.toMoneyAccount(moneyAccount.getUser()))
                .switchIfEmpty(Mono.error(new RuntimeException("Money account not found")));
    }

    @Override
    public Mono<MoneyAccount> save(MoneyAccount moneyAccount) {
        return moneyAccountRepository.save(newMoneyAccountDocument(moneyAccount))
                .map(moneyAccountDocument -> moneyAccountDocument.toMoneyAccount(moneyAccount.getUser()));
    }

    private MoneyAccountDocument newMoneyAccountDocument(MoneyAccount moneyAccount) {
        return MoneyAccountDocument.builder()
                .balance(moneyAccount.getBalance())
                .userId(moneyAccount.getUser().getId())
                .build();
    }
}
