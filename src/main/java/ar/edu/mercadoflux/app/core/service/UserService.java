package ar.edu.mercadoflux.app.core.service;

import ar.edu.mercadoflux.app.core.domain.MoneyAccount;
import ar.edu.mercadoflux.app.core.domain.UserType;
import ar.edu.mercadoflux.app.core.exception.UserAlreadyExistsException;
import ar.edu.mercadoflux.app.core.exception.UserNotFoundException;
import ar.edu.mercadoflux.app.core.repository.UserRepository;
import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.dto.RegisterUser;
import ar.edu.mercadoflux.app.ports.input.web.dto.UpdateUserRequest;
import ar.edu.mercadoflux.app.core.usecase.user.GetUserUseCase;
import ar.edu.mercadoflux.app.core.usecase.user.RegisterUserUseCase;
import ar.edu.mercadoflux.app.core.usecase.user.UpdateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static ar.edu.mercadoflux.app.core.domain.UserType.CUSTOMER;
import static ar.edu.mercadoflux.app.core.domain.UserType.SELLER;


@Service
@RequiredArgsConstructor
public class UserService implements RegisterUserUseCase, GetUserUseCase, UpdateUserUseCase {

    private final UserRepository userRepository;
    private final MoneyAccountService moneyAccountService;

    public Mono<User> getUser(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException()));
    }

    public Mono<User> getUserForMail(String mail) {
        return userRepository.findByEmail(mail);
    }

    public Mono<User> registerUser(RegisterUser registerUser) {
        return handleExistingUser(registerUser)
                .switchIfEmpty(register(registerUser, CUSTOMER));
    }

    @Override
    public Mono<User> registerSellerUser(RegisterUser registerUser) {
        return handleExistingUser(registerUser)
                .switchIfEmpty(register(registerUser, SELLER));
    }

    private Mono<User> handleExistingUser(RegisterUser registerUser) {
        return userRepository.findByEmail(registerUser.getEmail())
                .hasElement()
                .filter(exists -> exists)
                .flatMap(rejectExistingUser());
    }
    private Function<Boolean, Mono<User>> rejectExistingUser() {
        return exists -> exists ? Mono.error(new UserAlreadyExistsException()) : Mono.empty();
    }

    public Mono<User> updateUser(UpdateUserRequest updateUserRequest) {
        return userRepository.findById(updateUserRequest.getId())
                .map(user -> {
                    user.setName(updateUserRequest.getName());
                    user.setLastName(updateUserRequest.getLastName());
                    user.setCuit(updateUserRequest.getCuit());
                    return user;
                }).doOnNext(userRepository::save);
    }

    private Mono<User> register(RegisterUser registerUser, UserType userType) {
        return userRepository.save(buildUser(registerUser, userType))
                .flatMap(moneyAccountService::registerAccount)
                .map(MoneyAccount::getUser);
    }

    private User buildUser(RegisterUser registerUser, UserType userType) {
        return User.builder()
                .name(registerUser.getName())
                .lastName(registerUser.getLastName())
                .email(registerUser.getEmail())
                .password(registerUser.getPassword())
                .type(userType)
                .cuit(registerUser.getCuit())
                .build();
    }
}
