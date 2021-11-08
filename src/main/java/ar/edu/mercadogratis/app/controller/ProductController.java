package ar.edu.mercadogratis.app.controller;

import ar.edu.mercadogratis.app.exceptions.NotFoundException;
import ar.edu.mercadogratis.app.model.Product;
import ar.edu.mercadogratis.app.model.ProductCategory;
import ar.edu.mercadogratis.app.model.SearchProductRequest;
import ar.edu.mercadogratis.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId)
                .orElseThrow(() -> new NotFoundException("product_not_found", "Product not found: " + productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("ok");
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @PutMapping
    public ResponseEntity<String> updateProduct(@Valid @RequestBody Product product) {
        productService.updateProduct(product);
        return ResponseEntity.ok("ok");
    }

    @GetMapping
    public Iterable<Product> listProducts(@RequestParam String seller) {
        return productService.listProducts(seller);
    }

    @GetMapping("/search")
    public Iterable<Product> searchProducts(@RequestParam Optional<String> name,
                                            @RequestParam Optional<ProductCategory> category,
                                            @RequestParam(name = "min_price") Optional<BigDecimal> minPrice,
                                            @RequestParam(name = "max_price") Optional<BigDecimal> maxPrice) {
        SearchProductRequest search = SearchProductRequest.builder()
                .category(category)
                .name(name)
                .maxPrice(maxPrice)
                .minPrice(minPrice)
                .build();
        return productService.searchProduct(search);
    }
}
