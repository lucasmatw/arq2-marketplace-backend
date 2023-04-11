package ar.edu.mercadoflux.app.core.usecase.purchase;

import ar.edu.mercadoflux.app.core.domain.Purchase;
import ar.edu.mercadoflux.app.core.dto.PurchaseProduct;
import reactor.core.publisher.Mono;

public interface PurchaseProductUseCase {
    Mono<Purchase> purchase(PurchaseProduct purchaseProduct);
}
