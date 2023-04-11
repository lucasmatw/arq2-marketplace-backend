package ar.edu.mercadoflux.app.core.repository;

import ar.edu.mercadoflux.app.core.domain.Purchase;
import ar.edu.mercadoflux.app.core.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PurchaseRepository {
    Flux<Purchase> findByBuyer(User buyer);
    Mono<Purchase> save(Purchase purchase);
}
