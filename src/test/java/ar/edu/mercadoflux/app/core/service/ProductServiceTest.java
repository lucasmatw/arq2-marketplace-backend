package ar.edu.mercadoflux.app.core.service;

import ar.edu.mercadoflux.app.core.domain.*;
import ar.edu.mercadoflux.app.core.dto.SaveProduct;
import ar.edu.mercadoflux.app.core.dto.UpdateProduct;
import ar.edu.mercadoflux.app.core.exception.NotSellerUserException;
import ar.edu.mercadoflux.app.core.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;


    @Test
    @DisplayName("Get a product by id")
    void getProductById() {
        // given
        String productId = "id";

        Product productMock = mock(Product.class);
        when(productRepository.findById(eq(productId))).thenReturn(Mono.just(productMock));

        // when
        Mono<Product> product = productService.getProduct(productId);

        // then
        assertThat(product.hasElement().block()).isTrue();
        assertThat(product.block()).isEqualTo(productMock);
    }

    @Test
    @DisplayName("Delete a product by setting is status to DELETED")
    void deleteProduct() {
        // given
        Product productMock = mock(Product.class);
        Product deletedProductMock = mock(Product.class);
        when(productRepository.save(productMock)).thenReturn(Mono.just(deletedProductMock));

        // when
        Mono<Product> product = productService.deleteProduct(productMock);

        // then
        assertThat(product.hasElement().block()).isTrue();
        assertThat(product.block()).isEqualTo(deletedProductMock);

        verify(productMock).setDeleted();
    }

    @Test
    @DisplayName("Saves a product - User is seller and ProductStatus is ACTIVE")
    void saveProduct() {
        // given
        SaveProduct saveProduct = SaveProduct.builder()
                .name("name")
                .description("description")
                .category(ProductCategory.FASHION)
                .price(BigDecimal.TEN)
                .stock(10)
                .seller(User.builder()
                        .type(UserType.SELLER)
                        .build())
                .build();

        Product expectedProductToSave = Product.builder()
                .name("name")
                .description("description")
                .category(ProductCategory.FASHION)
                .price(BigDecimal.TEN)
                .stock(10)
                .status(ProductStatus.ACTIVE)
                .seller(User.builder()
                        .type(UserType.SELLER)
                        .build())
                .build();

        Product savedProductMock = mock(Product.class);

        when(productRepository.save(argThat(ProductMatcher.of(expectedProductToSave))))
                .thenReturn(Mono.just(savedProductMock));

        // when
        Mono<Product> product = productService.saveProduct(saveProduct);

        // then
        assertThat(product.hasElement().block()).isTrue();
        assertThat(product.block()).isEqualTo(savedProductMock);
    }

    @Test
    @DisplayName("Error saving a product - User is not seller")
    void saveProductErrorNotSellerUser() {
        // given
        SaveProduct saveProduct = SaveProduct.builder()
                .seller(User.builder()
                        .type(UserType.CUSTOMER)
                        .build())
                .build();

        // when
        assertThatThrownBy(() -> productService.saveProduct(saveProduct))
                .isInstanceOf(NotSellerUserException.class);

        // then
        verifyNoInteractions(productRepository);
    }

    @Test
    @DisplayName("Updates a product")
    void updateProduct() {
        // given
        String productId = "id";
        UpdateProduct updateProduct = UpdateProduct.builder()
                .id(productId)
                .name("new name")
                .description("new description")
                .category(ProductCategory.TECHNOLOGY)
                .price(BigDecimal.ONE)
                .stock(50)
                .build();

        Product savedProduct = Product.builder()
                .id(productId)
                .name("name")
                .description("description")
                .category(ProductCategory.FASHION)
                .price(BigDecimal.TEN)
                .stock(10)
                .status(ProductStatus.ACTIVE)
                .seller(User.builder()
                        .type(UserType.SELLER)
                        .build())
                .build();

        Product expectedProductToSave = Product.builder()
                .id(productId)
                .name("new name")
                .description("new description")
                .category(ProductCategory.TECHNOLOGY)
                .price(BigDecimal.ONE)
                .stock(50)
                .status(ProductStatus.ACTIVE)
                .seller(User.builder()
                        .type(UserType.SELLER)
                        .build())
                .build();

        Product savedProductMock = mock(Product.class);

        when(productRepository.findById(eq(productId))).thenReturn(Mono.just(savedProduct));
        when(productRepository.save(argThat(ProductMatcher.of(expectedProductToSave))))
                .thenReturn(Mono.just(savedProductMock));

        // when
        Mono<Product> product = productService.upateProduct(updateProduct);

        // then
        assertThat(product.hasElement().block()).isTrue();
        assertThat(product.block()).isEqualTo(savedProductMock);
    }

    @AllArgsConstructor(staticName = "of")
    static class ProductMatcher implements ArgumentMatcher<Product> {
        private final Product product;
        @Override
        public boolean matches(Product argument) {
            return argument.getName().equals(product.getName())
                    && argument.getDescription().equals(product.getDescription())
                    && argument.getCategory().equals(product.getCategory())
                    && argument.getPrice().equals(product.getPrice())
                    && argument.getStock() == product.getStock()
                    && argument.getStatus().equals(product.getStatus())
                    && argument.getSeller().equals(product.getSeller());
        }
    }
}