package ar.edu.mercadoflux.app.core.exception;

public class NotSellerUserException extends CoreException {
    public NotSellerUserException() {
        super("not_seller_user", "User is not a seller");
    }
}
