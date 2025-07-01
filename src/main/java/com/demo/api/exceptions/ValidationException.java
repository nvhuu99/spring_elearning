package com.demo.api.exceptions;

import com.demo.api.exceptions.errors.ErrorsBag;
import com.demo.api.responses.ApiResponse;
import com.demo.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationException {
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFound(NotFoundException exception) {
        return ApiResponse.notFound(exception.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handle(MethodArgumentNotValidException exception) {
        return ApiResponse.invalid(
                ErrorsBag.parseValidationException(exception).getErrors()
        );
    }
}
