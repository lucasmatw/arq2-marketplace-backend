package ar.edu.mercadoflux.app.ports.input.web.adapter;

import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.core.dto.SaveProduct;
import ar.edu.mercadoflux.app.core.dto.UpdateProduct;
import ar.edu.mercadoflux.app.core.service.UserService;
import ar.edu.mercadoflux.app.ports.input.web.dto.SaveProductRequest;
import ar.edu.mercadoflux.app.ports.input.web.dto.SaveProductResponse;
import ar.edu.mercadoflux.app.ports.input.web.dto.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductAdapter {
    private final UserService userService;

    public Mono<SaveProduct> toSaveProduct(SaveProductRequest saveProductRequest) {
        return userService.getUser(saveProductRequest.getSellerId())
                .map(user -> SaveProduct.builder()
                        .name(saveProductRequest.getName())
                        .description(saveProductRequest.getDescription())
                        .category(saveProductRequest.getCategory())
                        .price(saveProductRequest.getPrice())
                        .stock(saveProductRequest.getStock())
                        .seller(user)
                        .build());
    }

    public SaveProductResponse toSaveProductResponse(Product product) {
        return SaveProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    public UpdateProduct toUpdateProduct(UpdateProductRequest updateProductRequest) {
        return UpdateProduct.builder()
                .id(updateProductRequest.getId())
                .name(updateProductRequest.getName())
                .description(updateProductRequest.getDescription())
                .category(updateProductRequest.getCategory())
                .price(updateProductRequest.getPrice())
                .stock(updateProductRequest.getStock())
                .build();
    }
}
