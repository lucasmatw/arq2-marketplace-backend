package ar.edu.mercadoflux.app.core.service;

import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.core.domain.Purchase;
import ar.edu.mercadoflux.app.core.domain.PurchaseStatus;
import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.dto.PurchaseProduct;
import ar.edu.mercadoflux.app.core.dto.UpdateProduct;
import ar.edu.mercadoflux.app.core.repository.PurchaseRepository;
import ar.edu.mercadoflux.app.core.usecase.purchase.PurchaseProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchaseService implements PurchaseProductUseCase {

    private final UserService userService;
    private final ProductService productService;
    private final PurchaseRepository purchaseRepository;
    private final DateService dateService;
    private final MoneyAccountService moneyAccountService;


    @Override
    public Mono<Purchase> purchase(PurchaseProduct purchaseProduct) {
        return takeStock(purchaseProduct)
                .map(product -> createPurchase(purchaseProduct, product))
                .flatMap(this::registerTransaction)
                .flatMap(this::savePurchase);
    }

    public Flux<Purchase> listPurchases(String buyerEmail) {
        return getUser(buyerEmail)
                .flatMapMany(purchaseRepository::findByBuyer);
    }

    private Mono<Purchase> savePurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    private Mono<Purchase> registerTransaction(Purchase purchase) {
        User buyer = purchase.getBuyer();
        User seller = purchase.getProduct().getSeller();

        Mono<BigDecimal> creditAction = moneyAccountService.creditAmount(seller, purchase.getPrice());
        Mono<BigDecimal> debitAction = moneyAccountService.debitAmount(buyer, purchase.getPrice());

        return Mono.zip(creditAction, debitAction)
                .map(zip -> purchase);
    }

    private Purchase createPurchase(PurchaseProduct purchaseProduct, Product product) {
        LocalDateTime creationDate = dateService.getNowDate();
        return Purchase.builder()
                .product(product)
                .buyer(purchaseProduct.getBuyer())
                .creationDate(creationDate)
                .quantity(purchaseProduct.getQuantity())
                .status(PurchaseStatus.PENDING)
                .build();
    }

    private Mono<Product> takeStock(PurchaseProduct purchaseProduct) {
        Product product = purchaseProduct.getProduct()
                .takeStock(purchaseProduct.getQuantity());
        return productService.upateProduct(toUpdateProduct(product));
    }

    private Mono<User> getUser(String email) {
        return userService.getUserForMail(email);
    }

    private UpdateProduct toUpdateProduct(Product product) {
        return UpdateProduct.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
