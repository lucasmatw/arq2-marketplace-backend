package ar.edu.mercadoflux.app.core.service;

import ar.edu.mercadoflux.app.core.domain.ProductStatus;
import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.dto.SaveProduct;
import ar.edu.mercadoflux.app.core.dto.UpdateProduct;
import ar.edu.mercadoflux.app.core.exception.InvalidProductException;
import ar.edu.mercadoflux.app.core.exception.NotSellerUserException;
import ar.edu.mercadoflux.app.core.exception.ProductNotFoundException;
import ar.edu.mercadoflux.app.core.exception.UserNotFoundException;
import ar.edu.mercadoflux.app.core.repository.ProductRepository;
import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.core.usecase.product.DeleteProductUseCase;
import ar.edu.mercadoflux.app.core.usecase.product.GetProductUseCase;
import ar.edu.mercadoflux.app.core.usecase.product.SaveProductUseCase;
import ar.edu.mercadoflux.app.core.usecase.product.UpdateProductUseCase;
import ar.edu.mercadoflux.app.ports.input.web.dto.SaveProductRequest;
import ar.edu.mercadoflux.app.ports.input.web.dto.SaveProductResponse;
import ar.edu.mercadoflux.app.ports.input.web.dto.SearchProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static ar.edu.mercadoflux.app.core.domain.UserType.SELLER;


@RequiredArgsConstructor
@Service
public class ProductService implements GetProductUseCase, SaveProductUseCase,
        DeleteProductUseCase, UpdateProductUseCase {

    private final ProductRepository productRepository;


    @Override
    public Mono<Product> deleteProduct(Product product) {
        product.setDeleted();
        return productRepository.save(product);
    }

    @Override
    public Mono<Product> saveProduct(SaveProduct saveProduct) {
        validateIsSellerUser(saveProduct);
        return productRepository.save(toProduct(saveProduct));
    }

    public Mono<Product> getProduct(String productId) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new ProductNotFoundException()));
    }

    public Mono<Product> upateProduct(UpdateProduct updateProduct) {
        return productRepository.findById(updateProduct.getId())
                .map(product -> product.toBuilder()
                        .name(updateProduct.getName())
                        .description(updateProduct.getDescription())
                        .category(updateProduct.getCategory())
                        .price(updateProduct.getPrice())
                        .stock(updateProduct.getStock())
                        .build())
                .flatMap(productRepository::save)
                .switchIfEmpty(Mono.error(new ProductNotFoundException()));
    }

    public Flux<Product> listProducts(String seller) {
        return productRepository.findBySeller(seller);
    }


    private void validateIsSellerUser(SaveProduct saveProduct) {
        if (Objects.isNull(saveProduct.getSeller())) {
            throw new InvalidProductException("Seller is required");
        }
        if (!saveProduct.getSeller().getType().equals(SELLER)) {
            throw new NotSellerUserException();
        }
    }

    private Product toProduct(SaveProduct saveProduct) {
        return Product.builder()
                .name(saveProduct.getName())
                .description(saveProduct.getDescription())
                .category(saveProduct.getCategory())
                .price(saveProduct.getPrice())
                .stock(saveProduct.getStock())
                .status(ProductStatus.ACTIVE)
                .seller(saveProduct.getSeller())
                .build();
    }
}
