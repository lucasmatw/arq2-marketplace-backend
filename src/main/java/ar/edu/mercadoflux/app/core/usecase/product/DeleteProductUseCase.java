package ar.edu.mercadoflux.app.core.usecase.product;

import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.ports.input.web.dto.SaveProductRequest;
import ar.edu.mercadoflux.app.ports.input.web.dto.SaveProductResponse;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface DeleteProductUseCase {
    Mono<Product> deleteProduct(Product product);
}
