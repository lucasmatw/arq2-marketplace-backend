package ar.edu.mercadoflux.app.core.exception;

public class InvalidProductException extends CoreException {
    public InvalidProductException(String message) {
        super("invalid_product", message);
    }
}
