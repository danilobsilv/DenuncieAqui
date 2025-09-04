package com.api.denuncieAqui.infra.exceptionsHandler;

import com.api.denuncieAqui.infra.exceptionsHandler.resourceConflictException.ResourceConflictException;
import com.api.denuncieAqui.infra.exceptionsHandler.resourceNotFoundException.ResourceNotFoundException;
import com.api.denuncieAqui.infra.exceptionsHandler.resourceNotFoundException.ResourceNotFoundType;
import com.api.denuncieAqui.infra.exceptionsHandler.validationError.ValidationErrorException;
import com.api.denuncieAqui.infra.exceptionsHandler.validationError.ValidationErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResourceNotFoundType> handleUnexpectedException(Exception exception) {

    ResourceNotFoundType errorType = new ResourceNotFoundType(
            Instant.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ErrorTypes.INTERNAL_SERVER_ERROR,
            "Ocorreu um erro inesperado no servidor. Tente novamente mais tarde."
    );
    return new ResponseEntity<>(errorType, HttpStatus.INTERNAL_SERVER_ERROR);
}

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<ResourceNotFoundType> handleResourceNotFound(ResourceNotFoundException exception){
        ResourceNotFoundType errorType = new ResourceNotFoundType(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                ErrorTypes.RESOURCE_NOT_FOUND,
                exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorType);
    }

    @ExceptionHandler(ValidationErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorType> handleValidationErrors(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

        ValidationErrorType errorDetails = new ValidationErrorType(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                ErrorTypes.VALIDATION_ERROR,
                "Ocorreram erros de validação.",
                errors
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ResourceNotFoundType> handleResourceConflict(ResourceConflictException exception) {
    ResourceNotFoundType errorType = new ResourceNotFoundType(
            Instant.now(),
            HttpStatus.CONFLICT.value(),
            ErrorTypes.RESOURCE_CONFLICT,
            exception.getMessage()
    );
    return new ResponseEntity<>(errorType, HttpStatus.CONFLICT);
}

}
