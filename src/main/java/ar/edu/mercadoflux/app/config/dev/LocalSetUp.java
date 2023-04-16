package ar.edu.mercadoflux.app.config.dev;

import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.core.domain.ProductCategory;
import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.dto.RegisterUser;
import ar.edu.mercadoflux.app.core.dto.SaveProduct;
import ar.edu.mercadoflux.app.core.usecase.moneyaccount.AddFundsUseCase;
import ar.edu.mercadoflux.app.core.usecase.product.SaveProductUseCase;
import ar.edu.mercadoflux.app.core.usecase.user.RegisterUserUseCase;
import ar.edu.mercadoflux.app.ports.output.persistence.repository.mongo.ReactiveMoneyAccountRepository;
import ar.edu.mercadoflux.app.ports.output.persistence.repository.mongo.ReactiveProductRepository;
import ar.edu.mercadoflux.app.ports.output.persistence.repository.mongo.ReactiveUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Profile("dev")
@Component
@Log4j2
@RequiredArgsConstructor
public class LocalSetUp {

    private final ReactiveUserRepository userRepository;
    private final ReactiveMoneyAccountRepository moneyAccountRepository;
    private final ReactiveProductRepository productRepository;
    private final RegisterUserUseCase registerUserUseCase;
    private final SaveProductUseCase saveProductUseCase;
    private final AddFundsUseCase addFundsUseCase;

    @PostConstruct
    public void setUpTestingData() {
        log.info("Setting up testing data");

        userRepository.deleteAll()
                .and(moneyAccountRepository.deleteAll())
                .and(productRepository.deleteAll())
                .then(setUpBuyerUser())
                .thenMany(setUpSellerUser()
                        .flatMapMany(this::setUpProducts))
                .doOnComplete(() -> log.info("Set up complete"))
                .subscribe();
    }

    private Flux<Product> setUpProducts(User seller) {
        List<Mono<Product>> productMonos = buildProducts(seller).stream()
                .map(saveProductUseCase::saveProduct)
                .collect(Collectors.toList());
        return Flux.concat(productMonos);
    }

    private List<SaveProduct> buildProducts(User seller) {
        return List.of(
                SaveProduct.builder()
                        .seller(seller)
                        .name("Product 1")
                        .description("Description 1")
                        .category(ProductCategory.FASHION)
                        .price(BigDecimal.valueOf(1000))
                        .stock(10)
                        .build(),
                SaveProduct.builder()
                        .seller(seller)
                        .name("Product 2")
                        .description("Description 2")
                        .price(BigDecimal.valueOf(2350))
                        .category(ProductCategory.FASHION)
                        .stock(20)
                        .build(),
                SaveProduct.builder()
                        .seller(seller)
                        .name("Product 3")
                        .description("Description 3")
                        .category(ProductCategory.ELECTRICAL_APPLIANCE)
                        .price(BigDecimal.valueOf(200000))
                        .stock(3)
                        .build()
        );
    }

    private Mono<User> setUpBuyerUser() {
        RegisterUser buyer = RegisterUser.builder()
                .name("Pedro Buyer")
                .password("buyer")
                .email("buyer@gmail.com")
                .build();

        return registerUserUseCase.registerUser(buyer)
                .doOnSuccess(user -> addFundsUseCase.addFunds(user.getId(), BigDecimal.valueOf(100000)).subscribe())
                .log("add funds complete");
    }

    private Mono<User> setUpSellerUser() {
        RegisterUser seller = RegisterUser.builder()
                .name("Juan Seller")
                .password("seller")
                .email("seller@gmail.com")
                .cuit("123456789")
                .build();
        return registerUserUseCase.registerSellerUser(seller);
    }
}
