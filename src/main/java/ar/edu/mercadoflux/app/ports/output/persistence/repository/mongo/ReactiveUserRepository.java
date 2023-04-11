package ar.edu.mercadoflux.app.ports.output.persistence.repository.mongo;

import ar.edu.mercadoflux.app.ports.output.persistence.entities.UserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ReactiveUserRepository extends ReactiveMongoRepository<UserDocument, String> {
    Mono<UserDocument> findByEmail(String email);
}
