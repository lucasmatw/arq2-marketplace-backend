package ar.edu.mercadoflux.app.ports.output.persistence.repository.mongo;

import ar.edu.mercadoflux.app.core.domain.Purchase;
import ar.edu.mercadoflux.app.ports.output.persistence.entities.PurchaseDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ReactivePurchaseRepository extends ReactiveMongoRepository<PurchaseDocument, String> {
    Flux<Purchase> findByBuyerId(String buyerId);
}
