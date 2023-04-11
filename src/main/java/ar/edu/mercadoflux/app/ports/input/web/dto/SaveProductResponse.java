package ar.edu.mercadoflux.app.ports.input.web.dto;

import ar.edu.mercadoflux.app.core.domain.ProductCategory;
import ar.edu.mercadoflux.app.core.domain.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveProductResponse {
    private String id;
    private String name;
    private String description;
    private ProductCategory category;
    private BigDecimal price;
    private int stock;
    private ProductStatus status;
}
