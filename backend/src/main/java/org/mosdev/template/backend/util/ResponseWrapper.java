package org.mosdev.template.backend.util;

import org.mosdev.template.backend.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Utility class to standardize successful API responses.
 * This class provides methods to wrap any data in an ApiResponse object.
 */
public class ResponseWrapper {

    /**
     * Wrap data in an ApiResponse with HTTP 200 OK status
     *
     * @param data    The data to include in the response
     * @param method  The HTTP method used
     * @param message Additional message
     * @param <T>     The type of data
     * @return ResponseEntity containing ApiResponse
     */
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data, String method, String message) {
        ApiResponse<T> response = ApiResponse.success(data, method, message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Wrap data in an ApiResponse with HTTP 201 CREATED status
     *
     * @param data    The data to include in the response
     * @param method  The HTTP method used
     * @param message Additional message
     * @param <T>     The type of data
     * @return ResponseEntity containing ApiResponse
     */
    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String method, String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(HttpStatus.CREATED.value())
                .method(method)
                .timestamp(java.time.LocalDateTime.now())
                .success(true)
                .message(message)
                .data(data)
                .metadata(new java.util.HashMap<>())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Wrap data in an ApiResponse with HTTP 204 NO_CONTENT status
     *
     * @param method  The HTTP method used
     * @param message Additional message
     * @return ResponseEntity containing ApiResponse
     */
    public static <T> ResponseEntity<ApiResponse<T>> noContent(String method, String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .method(method)
                .timestamp(java.time.LocalDateTime.now())
                .success(true)
                .message(message)
                .metadata(new java.util.HashMap<>())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    /**
     * Wrap data in an ApiResponse with custom HTTP status
     *
     * @param data    The data to include in the response
     * @param status  The HTTP status
     * @param method  The HTTP method used
     * @param message Additional message
     * @param <T>     The type of data
     * @return ResponseEntity containing ApiResponse
     */
    public static <T> ResponseEntity<ApiResponse<T>> status(T data, HttpStatus status, String method, String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(status.value())
                .method(method)
                .timestamp(java.time.LocalDateTime.now())
                .success(status.is2xxSuccessful())
                .message(message)
                .data(data)
                .metadata(new java.util.HashMap<>())
                .build();
        return new ResponseEntity<>(response, status);
    }

    /**
     * Wrap data in an ApiResponse with HTTP 200 OK status and metadata
     *
     * @param data     The data to include in the response
     * @param method   The HTTP method used
     * @param message  Additional message
     * @param metadata Metadata as key-value pairs
     * @param <T>      The type of data
     * @return ResponseEntity containing ApiResponse
     */
    public static <T> ResponseEntity<ApiResponse<T>> okWithMetadata(T data, String method, String message, Map<String, Object> metadata) {
        ApiResponse<T> response = ApiResponse.success(data, method, message);
        metadata.forEach(response::addMetadata);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}