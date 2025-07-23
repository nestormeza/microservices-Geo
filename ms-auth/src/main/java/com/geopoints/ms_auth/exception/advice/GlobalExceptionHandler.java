package com.geopoints.ms_auth.exception.advice;

import com.geopoints.ms_auth.exception.GlobalException;
import com.geopoints.ms_auth.utils.response.ExceptionResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ExceptionResponse> handlerGlobalException(GlobalException exception, HttpServletRequest request){
        int code = exception.getCode();
        HttpStatus status = HttpStatus.resolve(code);


        //POR SI EL CODIGO ENVIADO ES INCORRECTO
        if (status == null || code < 400 || code > 599) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(status).body(new ExceptionResponse(
                code,
                exception.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        ));
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        return buildGenericResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        return buildGenericResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        return buildGenericResponse(HttpStatus.FORBIDDEN, "Acceso denegado", request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildGenericResponse(HttpStatus.BAD_REQUEST, errorMessage, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllUnhandled(Exception ex, HttpServletRequest request) {
        return buildGenericResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado: " + ex.getMessage(), request);
    }

    private ResponseEntity<ExceptionResponse> buildGenericResponse(HttpStatus status, String message, HttpServletRequest request) {
        return ResponseEntity.status(status).body(new ExceptionResponse(
                status.value(),
                message,
                LocalDateTime.now(),
                request.getRequestURI()
        ));
    }
}
