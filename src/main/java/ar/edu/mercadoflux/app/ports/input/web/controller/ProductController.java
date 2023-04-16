package ar.edu.mercadoflux.app.ports.input.web.controller;

import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.core.domain.ProductCategory;
import ar.edu.mercadoflux.app.core.dto.ProductSearchResult;
import ar.edu.mercadoflux.app.core.dto.SearchProduct;
import ar.edu.mercadoflux.app.core.usecase.product.*;
import ar.edu.mercadoflux.app.ports.input.web.adapter.ProductAdapter;
import ar.edu.mercadoflux.app.ports.input.web.dto.SaveProductRequest;
import ar.edu.mercadoflux.app.ports.input.web.dto.SaveProductResponse;
import ar.edu.mercadoflux.app.ports.input.web.dto.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductAdapter productAdapter;

    private final GetProductUseCase getProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final SaveProductUseCase saveProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final SearchProductUseCase searchProductUseCase;


    @GetMapping("/{productId}")
    public Mono<Product> getProduct(@PathVariable String productId) {
        return getProductUseCase.getProduct(productId);
    }

    @DeleteMapping("/{productId}")
    public Mono<SaveProductResponse> deleteProduct(@PathVariable String productId) {
        return getProductUseCase.getProduct(productId)
                .flatMap(deleteProductUseCase::deleteProduct)
                .map(productAdapter::toSaveProductResponse);
    }

    @PostMapping
    public Mono<SaveProductResponse> saveProduct(@RequestBody SaveProductRequest saveProductRequest) {
        return productAdapter.toSaveProduct(saveProductRequest)
                .flatMap(saveProductUseCase::saveProduct)
                .map(productAdapter::toSaveProductResponse);
    }

    @PutMapping
    public Mono<SaveProductResponse> updateProduct(@RequestBody UpdateProductRequest updateProductRequest) {
        return updateProductUseCase.upateProduct(productAdapter.toUpdateProduct(updateProductRequest))
                .map(productAdapter::toSaveProductResponse);
    }

    @GetMapping("/search")
    public Flux<ProductSearchResult> searchProducts(@RequestParam Optional<String> name,
                                                    @RequestParam Optional<ProductCategory> category,
                                                    @RequestParam Optional<BigDecimal> minPrice,
                                                    @RequestParam Optional<BigDecimal> maxPrice){
        SearchProduct search = new SearchProduct(name, category, minPrice, maxPrice);
        return searchProductUseCase.search(search);
    }
}
