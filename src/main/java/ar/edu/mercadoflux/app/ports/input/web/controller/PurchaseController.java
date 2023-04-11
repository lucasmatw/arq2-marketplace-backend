package ar.edu.mercadoflux.app.ports.input.web.controller;


import ar.edu.mercadoflux.app.core.domain.Purchase;
import ar.edu.mercadoflux.app.core.dto.PurchaseProduct;
import ar.edu.mercadoflux.app.core.usecase.purchase.PurchaseProductUseCase;
import ar.edu.mercadoflux.app.ports.input.web.adapter.PurchaseAdapter;
import ar.edu.mercadoflux.app.ports.input.web.dto.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseAdapter purchaseAdapter;
    private final PurchaseProductUseCase purchaseProductUseCase;

    @PostMapping
    public Mono<Purchase> createPurchase(@Valid @RequestBody PurchaseRequest purchaseRequest) {
        return toPurchaseProduct(purchaseRequest)
                .flatMap(purchaseProductUseCase::purchase);
    }

    @GetMapping
    public Flux<Purchase> listPurchases(@RequestParam String buyerEmail) {
        return null;// purchaseService.listPurchases(buyerEmail);
    }

    private Mono<PurchaseProduct> toPurchaseProduct(PurchaseRequest request) {
        return purchaseAdapter.toPurchaseProduct(request);
    }
}
