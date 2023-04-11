package ar.edu.mercadoflux.app.ports.input.web.dto;

import ar.edu.mercadoflux.app.core.domain.ProductCategory;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@Builder
public class SearchProductRequest {
    private Optional<String> name;
    private Optional<ProductCategory> category;
}
