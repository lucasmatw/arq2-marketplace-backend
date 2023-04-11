package ar.edu.mercadoflux.app.core.usecase.user;

import ar.edu.mercadoflux.app.core.domain.User;
import reactor.core.publisher.Mono;

public interface GetUserUseCase {
    Mono<User> getUserForMail(String mail);
    Mono<User> getUser(String id);
}
