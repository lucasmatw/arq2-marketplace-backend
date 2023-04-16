package ar.edu.mercadoflux.app.ports.input.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFundsRequest {

    @Min(1)
    private BigDecimal amount;
    @NotEmpty
    private String userId;
}