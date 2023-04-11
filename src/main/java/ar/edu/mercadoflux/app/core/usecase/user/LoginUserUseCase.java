package ar.edu.mercadoflux.app.core.usecase.user;

import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.dto.AuthUser;
import reactor.core.publisher.Mono;

public interface LoginUserUseCase {
    Mono<User> login(AuthUser authUser);
}
