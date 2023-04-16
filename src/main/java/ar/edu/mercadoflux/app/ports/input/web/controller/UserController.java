package ar.edu.mercadoflux.app.ports.input.web.controller;

import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.dto.AuthUser;
import ar.edu.mercadoflux.app.core.dto.DeleteUserRequest;
import ar.edu.mercadoflux.app.core.usecase.user.DeleteUserUseCase;
import ar.edu.mercadoflux.app.ports.input.web.dto.LoginRequest;
import ar.edu.mercadoflux.app.core.dto.RegisterUser;
import ar.edu.mercadoflux.app.core.usecase.user.LoginUserUseCase;
import ar.edu.mercadoflux.app.core.usecase.user.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @PostMapping("/register")
    public Mono<User> registerUser(@RequestBody RegisterUser registerUser) {
        return registerUserUseCase.registerUser(registerUser);
    }

    @PostMapping("/seller/register")
    public Mono<User> registerSellerUser(@RequestBody RegisterUser registerUser) {
        return registerUserUseCase.registerSellerUser(registerUser);
    }

    @PostMapping(value = "/login")
    public Mono<User> login(@RequestBody LoginRequest loginRequest) {
        return loginUserUseCase.login(getAuthRequest(loginRequest));
    }

    @DeleteMapping
    public Mono<User> delete(@RequestBody LoginRequest loginRequest) {
        return deleteUserUseCase.deleteUser(deleteUserRequest(loginRequest));
    }

    private AuthUser getAuthRequest(LoginRequest loginRequest) {
        return new AuthUser(loginRequest.email(), loginRequest.password());
    }

    private DeleteUserRequest deleteUserRequest(LoginRequest loginRequest) {
        return new DeleteUserRequest(getAuthRequest(loginRequest));
    }
}
