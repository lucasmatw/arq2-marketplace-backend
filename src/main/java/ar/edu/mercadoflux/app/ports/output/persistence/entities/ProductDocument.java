package ar.edu.mercadoflux.app.ports.output.persistence.entities;

import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.core.domain.ProductCategory;
import ar.edu.mercadoflux.app.core.domain.ProductStatus;
import ar.edu.mercadoflux.app.core.domain.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@Document(collection = "products")
public class ProductDocument {
    @MongoId
    private String id;
    private String name;
    private String description;
    private ProductCategory category;
    private BigDecimal price;
    private int stock;
    private String sellerId;
    private ProductStatus status;

    public Product toProduct(User seller) {
        return Product.builder()
                .id(id)
                .name(name)
                .description(description)
                .category(category)
                .price(price)
                .stock(stock)
                .seller(seller)
                .status(status)
                .build();
    }
}
