package ar.edu.mercadogratis.app.controller;


import ar.edu.mercadogratis.app.model.PurchaseProduct;
import ar.edu.mercadogratis.app.model.PurchaseRequest;
import ar.edu.mercadogratis.app.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public Mono<PurchaseProduct> createPurchase(@Valid @RequestBody PurchaseRequest purchaseRequest) {
        return purchaseService.createPurchase(purchaseRequest);
    }

    @GetMapping
    public Flux<PurchaseProduct> listPurchases(@RequestParam String buyerEmail) {
        return purchaseService.listPurchases(buyerEmail);
    }
}
