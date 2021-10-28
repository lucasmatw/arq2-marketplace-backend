package ar.edu.mercadogratis.app.dao;

import ar.edu.mercadogratis.app.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
