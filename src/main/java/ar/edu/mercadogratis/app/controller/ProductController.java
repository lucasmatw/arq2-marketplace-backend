package ar.edu.mercadogratis.app.controller;

import ar.edu.mercadogratis.app.model.Product;
import ar.edu.mercadogratis.app.model.ProductCategory;
import ar.edu.mercadogratis.app.model.SearchProductRequest;
import ar.edu.mercadogratis.app.service.ProductService;
import ar.edu.mercadogratis.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/{productId}")
    public Mono<Product> getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("ok");
    }

    @PostMapping
    public ResponseEntity<Mono<Product>> saveProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @PutMapping
    public ResponseEntity<String> updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
        return ResponseEntity.ok("ok");
    }

    @GetMapping
    public Flux<Product> listProducts(@RequestParam String seller) {
        return productService.listProducts(seller);
    }

    @GetMapping("/search")
    public Flux<Product> searchProducts(@RequestParam Optional<String> name,
                                        @RequestParam Optional<ProductCategory> category,
                                        @RequestParam(name = "min_price") Optional<BigDecimal> minPrice,
                                        @RequestParam(name = "max_price") Optional<BigDecimal> maxPrice) {
        SearchProductRequest search = SearchProductRequest.builder()
                .category(category)
                .name(name)
                .maxPrice(maxPrice)
                .minPrice(minPrice)
                .minStock(1)
                .build();
        return productService.searchProduct(search);
    }
}
