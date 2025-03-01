package backend.academy.passtracker.rest.advice;

import backend.academy.passtracker.core.exception.IllegalArgumentException;
import backend.academy.passtracker.core.exception.*;
import backend.academy.passtracker.rest.error.message.ApiErrorName;
import backend.academy.passtracker.rest.error.utils.ErrorResponseUtils;
import backend.academy.passtracker.rest.model.error.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public final class GlobalExceptionHandler {

    @ExceptionHandler(BadExtensionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleBadExtensionException(
            BadExtensionException e, WebRequest request) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, ApiErrorName.BAD_EXTENSION, e.getMessage(), request);
    }

    @ExceptionHandler(FileWithSuchNameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleFileWithSuchNameAlreadyExistException(
            FileWithSuchNameAlreadyExistException e, WebRequest request) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, ApiErrorName.FILE_ALREADY_EXISTS, e.getMessage(), request);
    }

    @ExceptionHandler(TooMuchFilesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleTooMuchFilesException(
            TooMuchFilesException e, WebRequest request) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, ApiErrorName.TO0_MANY_FILES, e.getMessage(), request);
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResponse> handleFileNotFoundException(
            FileNotFoundException e, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ApiErrorName.FILE_NOT_FOUND, e.getMessage(), request);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(
            BadRequestException e, WebRequest request) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, ApiErrorName.BAD_REQUEST, e.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {
        return ResponseEntity.badRequest()
                .body(ErrorResponseUtils.createValidationErrorResponse(ex, request));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {
        return ResponseEntity.badRequest()
                .body(ErrorResponseUtils.createConstraintViolationErrorResponse(ex.getConstraintViolations(), request));
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleEmailAlreadyUsedException(
            EmailAlreadyUsedException e, WebRequest request) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, ApiErrorName.EMAIL_ALREADY_USED, e.getMessage(), request);
    }

    @ExceptionHandler(FacultyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResponse> handleFacultyNotFoundException(
            FacultyNotFoundException e, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ApiErrorName.FACULTY_NOT_FOUND, e.getMessage(), request);
    }

    @ExceptionHandler(GroupNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResponse> handleGroupNotFoundException(
            GroupNotFoundException e, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ApiErrorName.GROUP_NOT_FOUND, e.getMessage(), request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(
            UserNotFoundException e, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ApiErrorName.USER_NOT_FOUND, e.getMessage(), request);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiErrorResponse> handleForbiddenException(
            ForbiddenException e, WebRequest request) {
        return createErrorResponse(HttpStatus.FORBIDDEN, ApiErrorName.FORBIDDEN, e.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiErrorResponse> handleException(
            Exception e, WebRequest request) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ApiErrorName.INTERNAL_SERVER_ERROR, e.getMessage(), request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException e, WebRequest request) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ApiErrorName.ILLEGAL_ARGUMENT_EXCEPTION, e.getMessage(), request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(
            AuthenticationException e,
            WebRequest request
    ) {

        return createErrorResponse(HttpStatus.UNAUTHORIZED, ApiErrorName.INVALID_AUTH_CREDENTIALS, e.getMessage(), request);
    }

    private ResponseEntity<ApiErrorResponse> createErrorResponse(
            HttpStatus status, String error, String message, WebRequest request) {
        ApiErrorResponse response = ErrorResponseUtils.createSimpleErrorResponse(
                status, error, message, request);
        return ResponseEntity.status(status).body(response);
    }
}