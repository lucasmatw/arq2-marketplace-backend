package ar.edu.mercadoflux.app.core.repository;

import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.core.dto.SearchProduct;
import ar.edu.mercadoflux.app.core.dto.SearchProductInternal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductRepository {
    Flux<Product> findBySeller(String seller);
    Mono<Product> findById(String id);
    Mono<Product> save(Product product);
    Flux<Product> search(SearchProductInternal searchProduct);
}
