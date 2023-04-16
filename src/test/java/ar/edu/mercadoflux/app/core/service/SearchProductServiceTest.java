package ar.edu.mercadoflux.app.core.service;

import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.core.domain.ProductCategory;
import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.dto.ProductSearchResult;
import ar.edu.mercadoflux.app.core.dto.SearchProduct;
import ar.edu.mercadoflux.app.core.dto.SearchProductInternal;
import ar.edu.mercadoflux.app.core.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchProductServiceTest {

    @InjectMocks
    private SearchProductService searchProductService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("Search product and maps correctly to product search result")
    void searchProducts() {
        // given
        SearchProduct searchProduct = new SearchProduct(Optional.of("name"), Optional.of(ProductCategory.FASHION),
                Optional.of(BigDecimal.ONE), Optional.of(BigDecimal.TEN));

        SearchProductInternal searchProductInternal = SearchProductInternal.of(searchProduct);

        Product productFound = Product.builder()
                .id("id")
                .name("name")
                .category(ProductCategory.FASHION)
                .price(BigDecimal.valueOf(5))
                .seller(User.builder()
                        .id("seller_id")
                        .name("seller name")
                        .build())
                .build();

        when(productRepository.search(searchProductInternal)).thenReturn(Flux.just(productFound));

        // when
        ProductSearchResult productSearchResult = searchProductService.search(searchProduct).blockFirst();

        // then
        assertNotNull(productSearchResult);
        assertEquals(productFound.getId(), productSearchResult.getId());
        assertEquals(productFound.getName(), productSearchResult.getName());
        assertEquals(productFound.getCategory(), productSearchResult.getCategory());
        assertEquals(productFound.getPrice(), productSearchResult.getPrice());
        assertEquals(productFound.getSeller().getId(), productSearchResult.getSellerInfo().getId());
        assertEquals(productFound.getSeller().getName(), productSearchResult.getSellerInfo().getName());
    }
}