package ar.edu.mercadogratis.app.controller;

import ar.edu.mercadogratis.app.model.Product;
import ar.edu.mercadogratis.app.model.ProductCategory;
import ar.edu.mercadogratis.app.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    private ProductService productService;


    @Test
    void testGetProduct() {

        // given
        String url = String.format("http://localhost:%s/products/%s", port, 1L);

        Product product = Product.builder()
                .name("name")
                        .description("description")
                                .category(ProductCategory.FASHION)
                                        .price(new BigDecimal("10"))
                                                .build();

        when(productService.getProduct(eq(1L))).thenReturn(Optional.of(product));

        // when
        ResponseEntity<Product> entity = restTemplate.getForEntity(url, Product.class);

        // then
        assertThat(entity.getBody()).isEqualTo(product);
    }
}