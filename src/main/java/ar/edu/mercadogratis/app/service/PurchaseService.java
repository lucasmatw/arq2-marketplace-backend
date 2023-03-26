package ar.edu.mercadogratis.app.service;

import ar.edu.mercadogratis.app.dao.PurchaseProductRepository;
import ar.edu.mercadogratis.app.exceptions.ValidationException;
import ar.edu.mercadogratis.app.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final UserService userService;
    private final ProductService productService;
    private final PurchaseProductRepository purchaseProductRepository;
    private final DateService dateService;
    private final MoneyAccountService moneyAccountService;


    public Flux<PurchaseProduct> listPurchases(String buyerEmail) {
        return getUser(buyerEmail)
                .flatMapMany(purchaseProductRepository::findByBuyer);
    }

    public Mono<PurchaseProduct> createPurchase(PurchaseRequest purchaseRequest) {
        return productService.getProduct(purchaseRequest.getProductId())
                .map(product -> takeStock(product, purchaseRequest.getQuantity()))
                .flatMap(product -> createPurchase(purchaseRequest, product))
                .flatMap(this::registerTransaction)
                .flatMap(this::savePurchase);

    }

    private Mono<PurchaseProduct> savePurchase(PurchaseProduct purchaseProduct){
        return purchaseProductRepository.save(purchaseProduct);
    }

    private Mono<PurchaseProduct> registerTransaction(PurchaseProduct purchase) {
        User buyer = purchase.getBuyer();
        User seller = purchase.getProduct().getSeller();

        Mono<BigDecimal> creditAction = moneyAccountService.creditAmount(seller, purchase.getPrice());
        Mono<BigDecimal> debitAction = moneyAccountService.debitAmount(buyer, purchase.getPrice());

        return Mono.zip(creditAction, debitAction)
                .map(zip -> purchase);
    }

    private Mono<PurchaseProduct> createPurchase(PurchaseRequest purchaseRequest, Product product) {
        return getUser(purchaseRequest.getBuyerEmail())
                .map(user -> {
                    LocalDateTime creationDate = dateService.getNowDate();
                    return new PurchaseProduct(product, user, creationDate, purchaseRequest.getQuantity(), PurchaseStatus.CONFIRMED);
                });

    }

    private Product takeStock(Product product, int quantity) {
        if (product.getStock() < quantity) {
            throw new ValidationException("insufficient_stock", "No stock available");
        }

        product.setStock(product.getStock() - quantity);
        productService.updateProduct(product);
        return product;
    }

    private Mono<User> getUser(String email) {
        return userService.getUserForMail(email);
    }
}
