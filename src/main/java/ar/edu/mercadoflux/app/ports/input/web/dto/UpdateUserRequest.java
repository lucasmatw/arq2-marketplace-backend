package ar.edu.mercadoflux.app.ports.input.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotEmpty
    private String id;
    @NotEmpty
    private String name;
    private String lastName;
    @NotEmpty
    private String email;
    private String cuit;
}
