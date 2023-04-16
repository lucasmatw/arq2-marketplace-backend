package ar.edu.mercadoflux.app.ports.input.web.adapter;

import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.core.domain.ProductReference;
import ar.edu.mercadoflux.app.core.domain.Purchase;
import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.dto.PurchaseProduct;
import ar.edu.mercadoflux.app.core.service.ProductService;
import ar.edu.mercadoflux.app.core.service.UserService;
import ar.edu.mercadoflux.app.ports.input.web.dto.PurchaseProductResponse;
import ar.edu.mercadoflux.app.ports.input.web.dto.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PurchaseAdapter {

    private final ProductService productService;
    private final UserService userService;

    public Mono<PurchaseProduct> toPurchaseProduct(PurchaseRequest request) {
        return userService.getUser(request.getBuyerId())
                .zipWith(productService.getProduct(request.getProductId()), (user, product) -> getPurchaseProduct(request, user, product));
    }

    public PurchaseProductResponse toPurchaseProductResponse(Purchase purchase) {
        return PurchaseProductResponse.builder()
                .id(purchase.getId())
                .product(toProductReferenceResponse(purchase))
                .creationDate(purchase.getCreationDate())
                .quantity(purchase.getQuantity())
                .status(purchase.getStatus())
                .build();
    }

    private PurchaseProduct getPurchaseProduct(PurchaseRequest request, User user, Product product) {
        return new PurchaseProduct(product, user, request.getQuantity());
    }

    private PurchaseProductResponse.ProductReferenceResponse toProductReferenceResponse(Purchase purchase) {
        ProductReference productReference = purchase.getProductReference();
        return PurchaseProductResponse.ProductReferenceResponse.builder()
                .id(productReference.getProductReferenceId())
                .name(productReference.getName())
                .description(productReference.getDescription())
                .category(productReference.getCategory().name())
                .price(productReference.getPrice())
                .sellerReference(toSellerReferenceResponse(purchase.getSeller()))
                .build();
    }

    private PurchaseProductResponse.SellerReferenceResponse toSellerReferenceResponse(User seller) {
        return PurchaseProductResponse.SellerReferenceResponse.builder()
                .id(seller.getId())
                .name(seller.getName())
                .build();
    }
}
