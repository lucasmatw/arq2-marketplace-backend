package ar.edu.mercadoflux.app.ports.input.web.dto;

import ar.edu.mercadoflux.app.core.domain.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
    private String id;
    private String name;
    private String description;
    private ProductCategory category;
    private BigDecimal price;
    private int stock;
}
