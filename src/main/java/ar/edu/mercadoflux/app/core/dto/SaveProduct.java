package ar.edu.mercadoflux.app.core.dto;

import ar.edu.mercadoflux.app.core.domain.ProductCategory;
import ar.edu.mercadoflux.app.core.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveProduct {
    private String name;
    private String description;
    private ProductCategory category;
    private BigDecimal price;
    private int stock;
    private User seller;
}
