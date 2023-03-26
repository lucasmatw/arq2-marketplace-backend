package ar.edu.mercadogratis.app.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@Document
public class User {

    @Id
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String cuit;

}
