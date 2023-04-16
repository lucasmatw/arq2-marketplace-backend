package ar.edu.mercadoflux.app.core.service;

import ar.edu.mercadoflux.app.core.domain.*;
import ar.edu.mercadoflux.app.core.dto.PurchaseProduct;
import ar.edu.mercadoflux.app.core.dto.UpdateProduct;
import ar.edu.mercadoflux.app.core.repository.PurchaseRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @InjectMocks
    private PurchaseService purchaseService;

    @Mock
    private ProductService productService;
    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private DateService dateService;
    @Mock
    private MoneyAccountService moneyAccountService;

    @Test
    @DisplayName("Purchase flow - happy path")
    void purchaseProduct() {
        // given
        Product product = mock(Product.class);
        User buyer = mock(User.class);
        User seller = mock(User.class);
        LocalDateTime now = LocalDateTime.now();
        BigDecimal price = BigDecimal.valueOf(500);

        PurchaseProduct purchaseProduct = new PurchaseProduct(product, buyer, 1);

        Purchase savedPurchase = mock(Purchase.class);

        when(seller.getId()).thenReturn("seller_id");
        when(seller.getName()).thenReturn("seller name");

        when(product.takeStock(eq(1))).thenReturn(product);
        when(product.getPrice()).thenReturn(price);
        when(product.getSeller()).thenReturn(seller);

        Purchase expectedPurchase = Purchase.builder()
                .productReference(ProductReference.fromProduct(product))
                .buyer(buyer)
                .seller(seller)
                .creationDate(now)
                .quantity(1)
                .status(PurchaseStatus.PENDING)
                .build();

        when(productService.upateProduct(any(UpdateProduct.class))).thenReturn(Mono.just(product));
        when(purchaseRepository.save(eq(expectedPurchase))).thenReturn(Mono.just(savedPurchase));

        when(dateService.getNowDate()).thenReturn(now);
        when(moneyAccountService.creditAmount(eq(seller), eq(price))).thenReturn(Mono.just(BigDecimal.ZERO));
        when(moneyAccountService.debitAmount(eq(buyer), eq(price))).thenReturn(Mono.just(BigDecimal.ZERO));

        // when
        Purchase purchase = purchaseService.purchase(purchaseProduct).block();

        // then
        assertThat(purchase).isEqualTo(savedPurchase);

        verify(moneyAccountService).creditAmount(eq(seller), eq(price));
        verify(moneyAccountService).debitAmount(eq(buyer), eq(price));
        verify(purchaseRepository).save(eq(expectedPurchase));
    }
}