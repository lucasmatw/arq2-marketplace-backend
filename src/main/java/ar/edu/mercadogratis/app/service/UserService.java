package ar.edu.mercadogratis.app.service;

import ar.edu.mercadogratis.app.dao.UserRepository;
import ar.edu.mercadogratis.app.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service("userService")
public class UserService {

    private final UserRepository userRepository;
    private final MoneyAccountService moneyAccountService;

    public Mono<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public Mono<User> getUserForMail(String mail) {
        return userRepository.searchUserByEmail(mail);
    }

    public Mono<Long> createUser(User user) {
        return userRepository.save(user)
                .doOnNext(moneyAccountService::registerAccount)
                .map(User::getId);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}
