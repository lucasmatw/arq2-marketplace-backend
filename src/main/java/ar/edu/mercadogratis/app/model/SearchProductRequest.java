package ar.edu.mercadogratis.app.model;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class SearchProductRequest {
    private String name;
    private Optional<ProductCategory> category;
}
