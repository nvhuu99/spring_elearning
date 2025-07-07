package com.demo.app.api.exceptions;

import com.demo.exceptions.errors.ErrorsBag;
import com.demo.app.api.responses.ApiResponse;
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
    public ResponseEntity<ApiResponse> handleInvalidArg(MethodArgumentNotValidException exception) {
        return ApiResponse.badRequest(
                ErrorsBag.parseValidationException(exception.getAllErrors()).getErrors()
        );
    }
}
