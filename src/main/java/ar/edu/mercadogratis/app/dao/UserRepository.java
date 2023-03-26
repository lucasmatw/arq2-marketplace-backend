package ar.edu.mercadogratis.app.dao;

import ar.edu.mercadogratis.app.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, Long> {
    @Query("select u from User u where u.email like %?1%")
    Mono<User> searchUserByEmail(String email);
}
