package ar.edu.mercadoflux.app.ports.input.web.dto;

import ar.edu.mercadoflux.app.core.domain.ProductCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
    @NotEmpty
    private String id;
    @NotEmpty
    private String name;
    private String description;
    @NotEmpty
    private ProductCategory category;
    @Min(1)
    private BigDecimal price;
    private int stock;
}
