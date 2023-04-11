package ar.edu.mercadoflux.app.core.exception;

public class UserAlreadyExistsException extends CoreException {
    public UserAlreadyExistsException() {
        super("user_already_exists", "User's email already exists");
    }
}
