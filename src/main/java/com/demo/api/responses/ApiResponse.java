package com.demo.api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
public class ApiResponse {
    private Boolean success;
    private String message;
    private Object data;
    private Object errors;

    public static ResponseEntity<ApiResponse> ok(Object data) {
        return ResponseEntity.ok(new ApiResponse(
                true,
                "",
                data,
                null
        ));
    }

    public static ResponseEntity<ApiResponse> created(Object data) {
        return new ResponseEntity<>(new ApiResponse(
                true,
                "",
                data,
                null
        ), HttpStatus.CREATED);
    }

    public static ResponseEntity<ApiResponse> noContent() {
        return new ResponseEntity<>(new ApiResponse(
                true,
                "",
                null,
                null
        ), HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity<ApiResponse> notFound(String message) {
        return new ResponseEntity<>(new ApiResponse(
                false,
                message,
                null,
                null
        ), HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<ApiResponse> invalid(Object errors) {
        return new ResponseEntity<>(new ApiResponse(
                false,
                null,
                null,
                errors
        ), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ApiResponse> exception(String message) {
        return new ResponseEntity<>(new ApiResponse(
                false,
                message,
                null,
                null
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
