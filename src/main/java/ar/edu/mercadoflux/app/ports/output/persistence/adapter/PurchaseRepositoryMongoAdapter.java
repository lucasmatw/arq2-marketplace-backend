package ar.edu.mercadoflux.app.ports.output.persistence.adapter;

import ar.edu.mercadoflux.app.core.domain.ProductReference;
import ar.edu.mercadoflux.app.core.domain.Purchase;
import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.repository.PurchaseRepository;
import ar.edu.mercadoflux.app.ports.output.persistence.entities.PurchaseDocument;
import ar.edu.mercadoflux.app.ports.output.persistence.repository.mongo.ReactivePurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PurchaseRepositoryMongoAdapter implements PurchaseRepository {

    private final ReactivePurchaseRepository purchaseRepository;

    @Override
    public Flux<Purchase> findByBuyer(User buyer) {
        return purchaseRepository.findByBuyerId(buyer.getId());
    }

    @Override
    public Mono<Purchase> save(Purchase purchase) {
        return purchaseRepository.save(toPurchaseDocument(purchase))
                .map(pd -> toPurchase(pd, purchase.getProductReference(), purchase.getBuyer(), purchase.getSeller()));
    }

    private PurchaseDocument toPurchaseDocument(Purchase purchase) {
        return PurchaseDocument.builder()
                .id(purchase.getId())
                .productReference(purchase.getProductReference())
                .buyerId(purchase.getBuyer().getId())
                .creationDate(purchase.getCreationDate())
                .quantity(purchase.getQuantity())
                .status(purchase.getStatus())
                .build();
    }

    private Purchase toPurchase(PurchaseDocument purchaseDocument, ProductReference productReference, User buyer, User seller) {
        return Purchase.builder()
                .id(purchaseDocument.getId())
                .productReference(productReference)
                .buyer(buyer)
                .seller(seller)
                .creationDate(purchaseDocument.getCreationDate())
                .quantity(purchaseDocument.getQuantity())
                .status(purchaseDocument.getStatus())
                .build();
    }
}
