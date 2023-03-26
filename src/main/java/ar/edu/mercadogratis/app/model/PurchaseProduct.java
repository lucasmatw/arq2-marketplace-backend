package ar.edu.mercadogratis.app.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document
public class PurchaseProduct extends BaseEntity {

    private Product product;

    private User buyer;

    private LocalDateTime creationDate;

    private int quantity;

    private PurchaseStatus status;

    public BigDecimal getPrice() {
        return product.getPrice();
    }
}
