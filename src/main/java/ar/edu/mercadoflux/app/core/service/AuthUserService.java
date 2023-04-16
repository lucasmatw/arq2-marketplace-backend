package ar.edu.mercadoflux.app.core.service;

import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.dto.AuthUser;
import ar.edu.mercadoflux.app.core.exception.InvalidUserOrPasswordException;
import ar.edu.mercadoflux.app.core.usecase.user.LoginUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthUserService implements LoginUserUseCase {

    private final UserService userService;

    public Mono<User> login(AuthUser authUser) {
        return userService.getUserForMail(authUser.getEmail())
                .filter(user -> user.getPassword().equals(authUser.getPassword()))
                .switchIfEmpty(Mono.error(new InvalidUserOrPasswordException()));
    }
}
