package ar.edu.mercadoflux.app.core.usecase.user;

import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.dto.RegisterUser;
import reactor.core.publisher.Mono;

public interface RegisterUserUseCase {
    Mono<User> registerUser(RegisterUser registerUser);
    Mono<User> registerSellerUser(RegisterUser registerUser);
}
