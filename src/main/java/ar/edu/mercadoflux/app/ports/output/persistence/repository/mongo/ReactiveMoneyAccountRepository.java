package ar.edu.mercadoflux.app.ports.output.persistence.repository.mongo;

import ar.edu.mercadoflux.app.ports.output.persistence.entities.MoneyAccountDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ReactiveMoneyAccountRepository extends ReactiveMongoRepository<MoneyAccountDocument, String> {
    Mono<MoneyAccountDocument> findByUserId(String userId);
}
