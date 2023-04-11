package ar.edu.mercadoflux.app.ports.output.persistence.entities;

import ar.edu.mercadoflux.app.core.domain.PurchaseStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "purchases")
public class PurchaseDocument {
    @MongoId
    private String id;
    private String productId;
    private String buyerId;
    private LocalDateTime creationDate;
    private int quantity;
    private PurchaseStatus status;
}
