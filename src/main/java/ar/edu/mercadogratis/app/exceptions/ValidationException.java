package ar.edu.mercadogratis.app.exceptions;

public class ValidationException extends ApiException {
    public ValidationException(String code, String description) {
        super(code, description);
    }
}
