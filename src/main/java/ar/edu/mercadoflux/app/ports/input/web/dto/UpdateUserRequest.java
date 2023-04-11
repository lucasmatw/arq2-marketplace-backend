package ar.edu.mercadoflux.app.ports.input.web.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String cuit;
}
