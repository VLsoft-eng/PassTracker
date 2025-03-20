package backend.academy.passtracker.rest.error.utils;

import backend.academy.passtracker.rest.model.error.ApiErrorResponse;
import jakarta.validation.ConstraintViolation;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
public final class ErrorResponseUtils {

    private static final String VALIDATION_ERROR_NAME = "Validation error";
    private static final String VALIDATION_ERROR_MESSAGE = "There were validation errors in the request";

    public static ApiErrorResponse createValidationErrorResponse(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(field, errorMessage);
        });

        return createErrorResponse(
                HttpStatus.BAD_REQUEST,
                VALIDATION_ERROR_NAME,
                VALIDATION_ERROR_MESSAGE,
                errors,
                request
        );
    }

    public static ApiErrorResponse createConstraintViolationErrorResponse(
            Set<ConstraintViolation<?>> violations,
            WebRequest request
    ) {

        Map<String, String> errors = new HashMap<>();
        violations.forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });

        return createErrorResponse(
                HttpStatus.BAD_REQUEST,
                VALIDATION_ERROR_NAME,
                VALIDATION_ERROR_MESSAGE,
                errors,
                request
        );
    }

    public static ApiErrorResponse createSimpleErrorResponse(
            HttpStatus status,
            String error,
            String message,
            WebRequest request
    ) {

        return createErrorResponse(status, error, message, Map.of(), request);
    }

    private static ApiErrorResponse createErrorResponse(
            HttpStatus status,
            String error,
            String message,
            Map<String, String> details,
            WebRequest request
    ) {

        return new ApiErrorResponse(
                LocalDateTime.now(),
                status.value(),
                error,
                message,
                details,
                request.getDescription(false)
        );
    }
}