package ar.edu.mercadoflux.app.core.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private String errorCode;
    private String message;
    private Integer status;
}
