package ar.edu.mercadogratis.app.service;

import ar.edu.mercadogratis.app.dao.GenericDao;
import ar.edu.mercadogratis.app.dao.ProductRepository;
import ar.edu.mercadogratis.app.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }

    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public Iterable<Product> listProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
