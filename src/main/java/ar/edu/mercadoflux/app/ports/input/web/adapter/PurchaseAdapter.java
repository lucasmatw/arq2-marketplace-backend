package ar.edu.mercadoflux.app.ports.input.web.adapter;

import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.dto.PurchaseProduct;
import ar.edu.mercadoflux.app.core.service.ProductService;
import ar.edu.mercadoflux.app.core.service.UserService;
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

    private PurchaseProduct getPurchaseProduct(PurchaseRequest request, User user, Product product) {
        return new PurchaseProduct(product, user, request.getQuantity());
    }
}
