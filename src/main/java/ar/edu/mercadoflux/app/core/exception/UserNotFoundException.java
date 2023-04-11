package ar.edu.mercadoflux.app.core.exception;

public class UserNotFoundException extends CoreException{
    public UserNotFoundException() {
        super("user_not_found", "User not found");
    }
}
