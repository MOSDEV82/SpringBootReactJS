package org.mosdev.template.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * A standardized API response object that can be used across all API endpoints.
 * This class provides a consistent structure for all API responses.
 *
 * @param <T> The type of data being returned in the response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    /**
     * HTTP status code of the response
     */
    private int code;

    /**
     * HTTP method used for the request (GET, POST, PUT, DELETE, etc.)
     */
    private String method;

    /**
     * Timestamp when the response was generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * Flag indicating if the request was successful
     */
    private boolean success;

    /**
     * Message providing additional information about the response
     */
    private String message;

    /**
     * The actual data being returned
     */
    private T data;

    /**
     * Metadata as key-value pairs for additional information
     */
    private Map<String, Object> metadata;

    /**
     * Static factory method to create a successful response
     *
     * @param data    The data to include in the response
     * @param method  The HTTP method used
     * @param message Additional message
     * @param <T>     The type of data
     * @return A new ApiResponse instance
     */
    public static <T> ApiResponse<T> success(T data, String method, String message) {
        return ApiResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .method(method)
                .timestamp(LocalDateTime.now())
                .success(true)
                .message(message)
                .data(data)
                .metadata(new HashMap<>())
                .build();
    }

    /**
     * Static factory method to create an error response
     *
     * @param status  The HTTP status code
     * @param method  The HTTP method used
     * @param message Error message
     * @param <T>     The type of data
     * @return A new ApiResponse instance
     */
    public static <T> ApiResponse<T> error(HttpStatus status, String method, String message) {
        return ApiResponse.<T>builder()
                .code(status.value())
                .method(method)
                .timestamp(LocalDateTime.now())
                .success(false)
                .message(message)
                .metadata(new HashMap<>())
                .build();
    }

    /**
     * Add a metadata entry to the response
     *
     * @param key   The metadata key
     * @param value The metadata value
     * @return This ApiResponse instance for method chaining
     */
    public ApiResponse<T> addMetadata(String key, Object value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(key, value);
        return this;
    }
}