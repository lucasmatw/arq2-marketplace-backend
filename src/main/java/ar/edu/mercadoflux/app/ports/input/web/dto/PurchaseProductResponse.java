package ar.edu.mercadoflux.app.ports.input.web.dto;

import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.core.domain.PurchaseStatus;
import ar.edu.mercadoflux.app.core.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseProductResponse {

    private String id;
    private ProductReferenceResponse product;
    private LocalDateTime creationDate;
    private int quantity;
    private PurchaseStatus status;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductReferenceResponse {
        private String id;
        private String name;
        private String description;
        private String category;
        private BigDecimal price;
        private SellerReferenceResponse sellerReference;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SellerReferenceResponse {
        private String id;
        private String name;
        private String email;
    }
}
