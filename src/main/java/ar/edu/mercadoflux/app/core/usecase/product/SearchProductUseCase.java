package ar.edu.mercadoflux.app.core.usecase.product;

import ar.edu.mercadoflux.app.core.dto.ProductSearchResult;
import ar.edu.mercadoflux.app.core.dto.SearchProduct;
import reactor.core.publisher.Flux;

public interface SearchProductUseCase {
    Flux<ProductSearchResult> search(SearchProduct searchProduct);
}
