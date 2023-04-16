package ar.edu.mercadoflux.app.ports.input.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class PurchaseRequest {
    @NotEmpty
    private String productId;
    @NotEmpty
    private String buyerId;

    @Min(1)
    private Integer quantity;
}

