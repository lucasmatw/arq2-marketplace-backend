package ar.edu.mercadoflux.app.core.exception;

public class InvalidUserOrPasswordException extends CoreException {
    public InvalidUserOrPasswordException() {
        super("invalid_user", "Invalid user or password");
    }
}
