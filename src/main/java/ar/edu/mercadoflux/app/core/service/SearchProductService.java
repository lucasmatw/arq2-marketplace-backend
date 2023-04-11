package ar.edu.mercadoflux.app.core.service;

import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.core.dto.ProductSearchResult;
import ar.edu.mercadoflux.app.core.dto.SearchProduct;
import ar.edu.mercadoflux.app.core.repository.ProductRepository;
import ar.edu.mercadoflux.app.core.usecase.product.SearchProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class SearchProductService implements SearchProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public Flux<ProductSearchResult> search(SearchProduct searchProduct) {
        return productRepository.search(searchProduct)
                .map(this::toProductSearchResult);
    }

    private ProductSearchResult toProductSearchResult(Product product) {
        return new ProductSearchResult(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                new ProductSearchResult.SellerInfo(
                        product.getSeller().getId(),
                        product.getSeller().getName()
                )
        );
    }
}
