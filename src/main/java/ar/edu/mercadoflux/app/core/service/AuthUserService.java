package ar.edu.mercadoflux.app.core.service;

import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.domain.UserStatus;
import ar.edu.mercadoflux.app.core.dto.AuthUser;
import ar.edu.mercadoflux.app.core.dto.DeleteUserRequest;
import ar.edu.mercadoflux.app.core.exception.InvalidUserOrPasswordException;
import ar.edu.mercadoflux.app.core.usecase.user.DeleteUserUseCase;
import ar.edu.mercadoflux.app.core.usecase.user.LoginUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthUserService implements LoginUserUseCase, DeleteUserUseCase {

    private final UserService userService;
    private final ProductService productService;

    public Mono<User> login(AuthUser authUser) {
        return userService.getUserForMail(authUser.getEmail())
                .filter(User::isActive)
                .filter(user -> user.getPassword().equals(authUser.getPassword()))
                .switchIfEmpty(Mono.error(new InvalidUserOrPasswordException()));
    }

    @Override
    public Mono<User> deleteUser(DeleteUserRequest deleteRequest) {
        return login(deleteRequest.getAuthUser())
                .flatMap(userService::deleteUser)
                .doOnSuccess(user -> productService.deleteProductsByUser(user).subscribe())
                .switchIfEmpty(Mono.error(new InvalidUserOrPasswordException()));
    }
}
