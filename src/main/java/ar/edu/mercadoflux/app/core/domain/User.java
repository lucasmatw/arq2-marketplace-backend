package ar.edu.mercadoflux.app.core.domain;

import lombok.*;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String cuit;
    private UserType type;
}
