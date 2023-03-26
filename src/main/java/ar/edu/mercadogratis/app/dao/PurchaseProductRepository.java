package ar.edu.mercadogratis.app.dao;

import ar.edu.mercadogratis.app.model.PurchaseProduct;
import ar.edu.mercadogratis.app.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import reactor.core.publisher.Flux;

public interface PurchaseProductRepository extends ReactiveMongoRepository<PurchaseProduct, Long> {
    @Query("select p from PurchaseProduct p where p.buyer = ?1")
    Flux<PurchaseProduct> findByBuyer(User buyer);
}
