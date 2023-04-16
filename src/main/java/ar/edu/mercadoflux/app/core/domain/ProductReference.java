package ar.edu.mercadoflux.app.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductReference {
    private String productReferenceId;
    private String name;
    private String description;
    private ProductCategory category;
    private BigDecimal price;
    private SellerInfo sellerInfo;

    public static ProductReference fromProduct(Product product) {
        return ProductReference.builder()
                .productReferenceId(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .sellerInfo(new SellerInfo(product.getSeller().getId(), product.getSeller().getName()))
                .build();
    }
}
