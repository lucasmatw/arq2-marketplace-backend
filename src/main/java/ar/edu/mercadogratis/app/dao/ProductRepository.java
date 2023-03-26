package ar.edu.mercadogratis.app.dao;

import ar.edu.mercadogratis.app.model.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveMongoRepository<Product, Long> {
    @Query("select p from Product p where p.seller = ?1 and p.status <> 'DELETED'")
    Flux<Product> findBySeller(String seller);
}
