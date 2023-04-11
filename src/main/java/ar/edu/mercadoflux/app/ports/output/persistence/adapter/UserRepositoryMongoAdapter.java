package ar.edu.mercadoflux.app.ports.output.persistence.adapter;

import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.repository.UserRepository;
import ar.edu.mercadoflux.app.ports.output.persistence.entities.UserDocument;
import ar.edu.mercadoflux.app.ports.output.persistence.repository.mongo.ReactiveUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static ar.edu.mercadoflux.app.ports.output.persistence.entities.UserDocument.fromUser;

@Service
@RequiredArgsConstructor
public class UserRepositoryMongoAdapter implements UserRepository {

    private final ReactiveUserRepository userRepository;

    @Override
    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserDocument::toUser);
    }

    @Override
    public Mono<User> findById(String id) {
        return userRepository.findById(id).map(UserDocument::toUser);
    }

    @Override
    public Mono<User> save(User user) {
        return userRepository.save(fromUser(user))
                .map(UserDocument::toUser);
    }
}
