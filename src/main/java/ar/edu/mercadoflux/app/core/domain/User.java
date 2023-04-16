package ar.edu.mercadoflux.app.core.domain;

import lombok.*;

import static ar.edu.mercadoflux.app.core.domain.UserStatus.ACTIVE;
import static ar.edu.mercadoflux.app.core.domain.UserStatus.DELETED;


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
    private UserStatus status;

    public void setDeleted() {
        this.status = DELETED;
    }
    public boolean isActive() {
        return this.status == ACTIVE;
    }
}
