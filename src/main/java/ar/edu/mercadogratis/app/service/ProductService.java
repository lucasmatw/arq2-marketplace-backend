package ar.edu.mercadogratis.app.service;

import ar.edu.mercadogratis.app.dao.ProductRepository;
import ar.edu.mercadogratis.app.model.Product;
import ar.edu.mercadogratis.app.model.SearchProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Mono<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }

    public Mono<Product> saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Flux<Product> saveAllProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public Flux<Product> listProducts(String seller) {
        return productRepository.findBySeller(seller);
    }

    public Mono<Void> deleteProduct(Long productId) {
        return productRepository.deleteById(productId);
    }

    public Flux<Product> searchProduct(SearchProductRequest searchProductRequest) {
//        return productRepository.findAll(new ProductSpecification(searchProductRequest));
        return productRepository.findAll();
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }
}
