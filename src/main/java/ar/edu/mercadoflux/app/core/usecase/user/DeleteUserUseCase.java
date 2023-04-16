package ar.edu.mercadoflux.app.core.usecase.user;

import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.dto.DeleteUserRequest;
import reactor.core.publisher.Mono;

public interface DeleteUserUseCase {
    Mono<User> deleteUser(DeleteUserRequest deleteRequest);
}
