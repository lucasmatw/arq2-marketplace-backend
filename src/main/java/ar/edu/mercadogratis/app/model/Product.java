package ar.edu.mercadogratis.app.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document
public class Product {

    @Id
    private Long id;

    private String name;

    private String description;

    private ProductCategory category;

    private BigDecimal price;

    private int stock;

    private User seller;

    private ProductStatus status;

}
