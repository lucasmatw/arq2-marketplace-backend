package ar.edu.mercadoflux.app.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CoreException extends RuntimeException {
    private String code;
    private String description;
}
