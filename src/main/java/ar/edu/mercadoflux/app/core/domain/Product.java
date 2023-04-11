package ar.edu.mercadoflux.app.core.domain;

import ar.edu.mercadoflux.app.core.exception.NoStockException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    private String id;
    private String name;
    private String description;
    private ProductCategory category;
    private BigDecimal price;
    private int stock;
    private User seller;
    private ProductStatus status;

    public Product takeStock(int quantity) {
        // validate stock
        validateStock(quantity);
        return this.toBuilder().stock(this.stock - quantity).build();
    }

    // validation method to check if stock is enough, if not throw NoStockException
    public void validateStock(int quantity) {
        if (this.stock < quantity) {
            throw new NoStockException(getId());
        }
    }

}
