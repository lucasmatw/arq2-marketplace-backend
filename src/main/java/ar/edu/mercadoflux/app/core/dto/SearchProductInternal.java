package ar.edu.mercadoflux.app.core.dto;

import ar.edu.mercadoflux.app.core.domain.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchProductInternal {
    private SearchProduct searchProduct;
    private ProductStatus status;

    public static SearchProductInternal of(SearchProduct searchProduct) {
        return new SearchProductInternal(searchProduct, ProductStatus.ACTIVE);
    }
}
