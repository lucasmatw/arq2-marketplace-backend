package ar.edu.mercadogratis.app.dao;

import ar.edu.mercadogratis.app.model.Product;
import ar.edu.mercadogratis.app.model.ProductCategory;
import ar.edu.mercadogratis.app.model.SearchProductRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    @Query("select p from Product p where p.name like ?1%")
    List<Product> searchProductByName(String name);

    @Query("select p from Product p where p.name like ?1% and p.category = ?2")
    List<Product> searchProductByNameAndCategory(String name, ProductCategory category);
}
