package ar.edu.mercadoflux.app.ports.output.persistence.entities;

import ar.edu.mercadoflux.app.core.domain.User;
import ar.edu.mercadoflux.app.core.domain.UserType;
import lombok.Builder;
import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@Document(collection = "users")
public class UserDocument {

    @MongoId
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String cuit;
    private UserType userType;

    public User toUser() {
        return User.builder()
                .id(id)
                .name(name)
                .lastName(lastName)
                .email(email)
                .password(password)
                .cuit(cuit)
                .type(userType)
                .build();
    }
    public static UserDocument fromUser(User user) {
        return UserDocument.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .cuit(user.getCuit())
                .userType(user.getType())
                .build();
    }
}
