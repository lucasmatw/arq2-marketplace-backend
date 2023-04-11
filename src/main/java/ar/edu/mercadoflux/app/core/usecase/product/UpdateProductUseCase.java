package ar.edu.mercadoflux.app.core.usecase.product;

import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.core.dto.UpdateProduct;
import reactor.core.publisher.Mono;

public interface UpdateProductUseCase {
    Mono<Product> upateProduct(UpdateProduct updateProduct);
}
