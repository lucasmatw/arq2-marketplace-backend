package ar.edu.mercadoflux.app.core.dto;

import ar.edu.mercadoflux.app.core.domain.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@AllArgsConstructor
public class SearchProduct {
    private Optional<String> name;
    private Optional<ProductCategory> category;
    private Optional<BigDecimal> minPrice;
    private Optional<BigDecimal> maxPrice;
}
