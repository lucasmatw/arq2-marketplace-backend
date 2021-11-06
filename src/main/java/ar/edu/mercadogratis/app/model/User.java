package ar.edu.mercadogratis.app.model;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@ToString
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class User extends BaseEntity {

    private String name;
    private String lastName;
    //@Column(name = "email", nullable = false, unique = true)
    private String email;
    private String password;
    private String cuit;


}
