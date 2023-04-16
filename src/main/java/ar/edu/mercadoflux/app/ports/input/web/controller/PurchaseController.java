package ar.edu.mercadoflux.app.ports.input.web.controller;


import ar.edu.mercadoflux.app.core.dto.PurchaseProduct;
import ar.edu.mercadoflux.app.core.usecase.purchase.PurchaseProductUseCase;
import ar.edu.mercadoflux.app.ports.input.web.adapter.PurchaseAdapter;
import ar.edu.mercadoflux.app.ports.input.web.dto.PurchaseProductResponse;
import ar.edu.mercadoflux.app.ports.input.web.dto.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseAdapter purchaseAdapter;
    private final PurchaseProductUseCase purchaseProductUseCase;

    @PostMapping
    public Mono<PurchaseProductResponse> createPurchase(@RequestBody @Valid PurchaseRequest purchaseRequest) {
        return toPurchaseProduct(purchaseRequest)
                .flatMap(purchaseProductUseCase::purchase)
                .map(purchaseAdapter::toPurchaseProductResponse);
    }

    private Mono<PurchaseProduct> toPurchaseProduct(PurchaseRequest request) {
        return purchaseAdapter.toPurchaseProduct(request);
    }
}
