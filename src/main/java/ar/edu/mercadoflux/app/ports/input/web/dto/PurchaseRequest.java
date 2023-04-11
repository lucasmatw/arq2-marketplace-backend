package ar.edu.mercadoflux.app.ports.input.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseRequest {
    private String productId;
    private String buyerId;
    private int quantity;
}

