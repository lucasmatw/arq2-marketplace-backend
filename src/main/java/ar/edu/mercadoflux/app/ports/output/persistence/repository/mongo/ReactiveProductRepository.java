package ar.edu.mercadoflux.app.ports.output.persistence.repository.mongo;

import ar.edu.mercadoflux.app.ports.output.persistence.entities.ProductDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ReactiveProductRepository extends ReactiveMongoRepository<ProductDocument, String> {
    Flux<ProductDocument> findBySellerId(String sellerId);
}
