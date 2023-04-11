package ar.edu.mercadoflux.app.core.exception;

public class NotFoundException extends ApiException {
    public NotFoundException(String code, String description) {
        super(code, description);
    }
}
