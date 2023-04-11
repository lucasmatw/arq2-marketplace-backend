package ar.edu.mercadoflux.app.core.repository;

import ar.edu.mercadoflux.app.core.domain.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> findByEmail(String email);
    Mono<User> findById(String id);
    Mono<User> save(User user);
}
