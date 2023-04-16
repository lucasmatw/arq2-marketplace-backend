package ar.edu.mercadoflux.app.core.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Purchase {

    private String id;
    private ProductReference productReference;
    private User buyer;
    private User seller;
    private LocalDateTime creationDate;
    private int quantity;
    private PurchaseStatus status;

    public BigDecimal getPrice() {
        return productReference.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
