package backend.academy.passtracker.rest.advice;

import backend.academy.passtracker.core.exception.*;
import backend.academy.passtracker.core.exception.IllegalArgumentException;
import backend.academy.passtracker.rest.model.common.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public final class GlobalExceptionHandler {

    @ExceptionHandler(BadExtensionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleBadExtensionException(BadExtensionException e) {
        return new ResponseEntity<>(new Response(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileWithSuchNameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleFileWithSuchNameAlreadyExistException(
            FileWithSuchNameAlreadyExistException e) {
        return new ResponseEntity<>(new Response(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TooMuchFilesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleTooMuchFilesException(TooMuchFilesException e) {
        return new ResponseEntity<>(new Response(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleFileNotFoundException(FileNotFoundException e) {
        return new ResponseEntity<>(new Response(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                e.getMessage()
        ), HttpStatus.NOT_FOUND);
    }

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

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleUserNotFoundException(UserNotFoundException e) {
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

    @ExceptionHandler(backend.academy.passtracker.core.exception.IllegalArgumentException.class)
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
