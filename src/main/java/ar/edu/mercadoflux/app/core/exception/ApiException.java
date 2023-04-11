package ar.edu.mercadoflux.app.core.exception;

public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String code;
    private final String description;

    public ApiException(String code, String description) {
        super(code);
        this.code = code;
        this.description = description;
    }

    public ApiException(String code, String description, Throwable cause) {
        super(code, cause);
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String getMessage() {
        return String.format("%s - %s", code, description);
    }
}
