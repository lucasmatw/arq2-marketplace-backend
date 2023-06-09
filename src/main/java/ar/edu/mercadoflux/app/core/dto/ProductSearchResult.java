package ar.edu.mercadoflux.app.core.dto;

import ar.edu.mercadoflux.app.core.domain.ProductCategory;
import ar.edu.mercadoflux.app.core.domain.SellerInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchResult {

    private String id;
    private String name;
    private String description;
    private ProductCategory category;
    private BigDecimal price;
    private int stock;
    private SellerInfo sellerInfo;
}
