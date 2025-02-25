package backend.academy.passtracker.core.exception;

import backend.academy.passtracker.rest.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public final class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new Response(
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now(),
                        e.getMessage()
                )
        );
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleEmailAlreadyUsedException(EmailAlreadyUsedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new Response(
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now(),
                        e.getMessage()
                )
        );
    }

    @ExceptionHandler(FacultyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleAccountNotFoundException(FacultyNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new Response(
                        HttpStatus.NOT_FOUND.value(),
                        LocalDateTime.now(),
                        e.getMessage()
                )
        );
    }

    @ExceptionHandler(GroupNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleOperationNotFoundException(GroupNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new Response(
                        HttpStatus.NOT_FOUND.value(),
                        LocalDateTime.now(),
                        e.getMessage()
                )
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Response> handleForbiddenException(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new Response(
                        HttpStatus.FORBIDDEN.value(),
                        LocalDateTime.now(),
                        e.getMessage()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new Response(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        LocalDateTime.now(),
                        e.getMessage()
                )
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new Response(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        LocalDateTime.now(),
                        e.getMessage()
                )
        );
    }
}
