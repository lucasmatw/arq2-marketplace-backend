package ar.edu.mercadoflux.app.core.usecase.user;

import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.ports.input.web.dto.UpdateUserRequest;
import reactor.core.publisher.Mono;

public interface UpdateUserUseCase {
    Mono<User> updateUser(UpdateUserRequest user);
}
