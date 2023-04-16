package ar.edu.mercadoflux.app.core.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {
    private String name;
    private String email;
    private String password;
    private String lastName;
    private String cuit;
}
