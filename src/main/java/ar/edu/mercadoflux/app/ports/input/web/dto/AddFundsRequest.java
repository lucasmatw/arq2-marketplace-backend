package ar.edu.mercadoflux.app.ports.input.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFundsRequest {
    private BigDecimal amount;
    private String userId;
}