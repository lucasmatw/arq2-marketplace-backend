package ar.edu.mercadoflux.app.ports.input.web.controller;

import ar.edu.mercadoflux.app.core.exception.ApiException;
import ar.edu.mercadoflux.app.core.exception.NotFoundException;
import ar.edu.mercadoflux.app.core.exception.CoreException;
import ar.edu.mercadoflux.app.core.exception.ApiError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException e) {
        return handleApiError(e, HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(CoreException.class)
    public ResponseEntity<ApiError> handleCoreException(CoreException e) {
        return handleApiError(buildBadRequestError(e.getCode(), e.getDescription()));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ApiError> handleValidationException(WebExchangeBindException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String description = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .reduce((s, s2) -> s + ", " + s2)
                .orElse("bad_request");
        return handleApiError(buildBadRequestError("bad_request", description));
    }

    public ResponseEntity<ApiError> handleApiError(ApiException e, int httpStatus) {
        return handleApiError(buildApiError(e, httpStatus));
    }

    public ResponseEntity<ApiError> handleApiError(ApiError apiError) {
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    private ApiError buildApiError(ApiException e, int status) {
        return ApiError.builder()
                .errorCode(e.getCode())
                .message(e.getDescription())
                .status(status)
                .build();
    }

    private ApiError buildBadRequestError(String errorCode, String message) {
        return ApiError.builder()
                .errorCode(errorCode)
                .message(message)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
