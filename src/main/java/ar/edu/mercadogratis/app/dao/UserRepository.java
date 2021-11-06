package ar.edu.mercadogratis.app.dao;

import ar.edu.mercadogratis.app.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("select u from User u where u.email like %?1%")
    Optional<User> searchUserByEmail(String email);
}
