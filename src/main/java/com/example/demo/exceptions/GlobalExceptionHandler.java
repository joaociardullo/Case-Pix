package com.example.demo.exceptions;

import com.example.demo.domain.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        var errorResponse = ErrorResponseDTO.builder()
                .status(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .message("Ocorreu um erro interno no servidor.")
                .erro(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        var errorResponse = ErrorResponseDTO.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .message(ex.getMessage())
                .erro(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        var errorResponse = ErrorResponseDTO.builder()
                .status(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .message(ex.getMessage())
                .erro(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {InvalidDataAccessResourceUsageException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponseDTO> handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException ex) {
        var errorResponse = ErrorResponseDTO.builder()
                .status(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .message(ex.getMessage())
                .erro(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
