package ar.edu.mercadoflux.app.core.usecase.product;

import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.core.dto.SaveProduct;
import reactor.core.publisher.Mono;

public interface SaveProductUseCase {
    Mono<Product> saveProduct(SaveProduct saveProduct);
}
