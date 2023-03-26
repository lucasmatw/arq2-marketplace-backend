package ar.edu.mercadogratis.app.controller;

import ar.edu.mercadogratis.app.exceptions.ValidationException;
import ar.edu.mercadogratis.app.model.User;
import ar.edu.mercadogratis.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping(value = "/user/register", method = RequestMethod.POST, headers = "Accept=application/json")
    public Mono<Long> registerUser(@RequestBody User user) {
        return userService.getUserForMail(user.getEmail())
                .map(User::getId)
                .switchIfEmpty(userService.createUser(user));
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST, headers = "Accept=application/json")
    public Mono<User> login(@RequestBody User loginUser) {
        return userService.getUserForMail(loginUser.getEmail())
                .flatMap(existingUser -> getAuthUser(existingUser, loginUser));
    }

    private Mono<User> getAuthUser(User existingUser, User loginUser) {
        return existingUser.getPassword().equals(loginUser.getPassword()) ?
                Mono.just(existingUser) : Mono.error(new ValidationException("invalid_user", "Invalid user or password"));
    }
}
