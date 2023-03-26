package ar.edu.mercadogratis.app.dao;

import ar.edu.mercadogratis.app.model.MoneyAccount;
import ar.edu.mercadogratis.app.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface MoneyAccountRepository extends ReactiveMongoRepository<MoneyAccount, Long> {

    @Query("select u from MoneyAccount u where u.user = ?1")
    Mono<MoneyAccount> getByUser(User user);
}
