package ar.edu.mercadogratis.app.controller;

import ar.edu.mercadogratis.app.exceptions.ApiException;
import ar.edu.mercadogratis.app.exceptions.NotFoundException;
import ar.edu.mercadogratis.app.exceptions.ValidationException;
import ar.edu.mercadogratis.app.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException e) {
        return handleErrorResponse(e, HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiError> handleValidationException(ValidationException e) {
        return handleErrorResponse(buildApiError(e));
    }

    public ResponseEntity<ApiError> handleErrorResponse(ApiException e, int httpStatus) {
        return handleErrorResponse(buildApiError(e, httpStatus));
    }

    public ResponseEntity<ApiError> handleErrorResponse(ApiError apiError) {
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    private ApiError buildApiError(ApiException e, int status) {
        return ApiError.builder()
                .cause(e.getCode())
                .message(e.getDescription())
                .status(status)
                .build();
    }

    private ApiError buildApiError(ValidationException validationException) {
        return ApiError.builder()
                .message(validationException.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
