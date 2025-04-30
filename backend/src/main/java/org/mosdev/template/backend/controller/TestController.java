package org.mosdev.template.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.mosdev.template.backend.model.ApiResponse;
import org.mosdev.template.backend.util.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test controller to demonstrate the use of ApiResponse and ResponseWrapper.
 * This controller provides endpoints for testing different response types.
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    /**
     * Test endpoint that returns a successful response
     */
    @GetMapping("/success")
    public ResponseEntity<ApiResponse<String>> getSuccessResponse(HttpServletRequest request) {
        return ResponseWrapper.ok("This is a successful response", request.getMethod(), "Operation completed successfully");
    }

    /**
     * Test endpoint that returns a response with data
     */
    @GetMapping("/data")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDataResponse(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Test User");
        data.put("email", "test@example.com");
        data.put("roles", List.of("USER", "ADMIN"));
        
        return ResponseWrapper.ok(data, request.getMethod(), "Data retrieved successfully");
    }

    /**
     * Test endpoint that returns a response with metadata
     */
    @GetMapping("/metadata")
    public ResponseEntity<ApiResponse<String>> getMetadataResponse(HttpServletRequest request) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("version", "1.0.0");
        metadata.put("server", "Test Server");
        metadata.put("timestamp", System.currentTimeMillis());
        
        return ResponseWrapper.okWithMetadata(
            "Response with metadata", 
            request.getMethod(), 
            "Metadata included in response", 
            metadata
        );
    }

    /**
     * Test endpoint that returns a created response
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> getCreatedResponse(HttpServletRequest request) {
        return ResponseWrapper.created("Resource created", request.getMethod(), "Resource created successfully");
    }

    /**
     * Test endpoint that returns a no content response
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> getNoContentResponse(HttpServletRequest request) {
        return ResponseWrapper.noContent(request.getMethod(), "Resource deleted successfully");
    }

    /**
     * Test endpoint that returns a custom status response
     */
    @GetMapping("/custom-status")
    public ResponseEntity<ApiResponse<String>> getCustomStatusResponse(HttpServletRequest request) {
        return ResponseWrapper.status(
            "Custom status response", 
            HttpStatus.ACCEPTED, 
            request.getMethod(), 
            "Request accepted for processing"
        );
    }

    /**
     * Test endpoint that throws an exception to demonstrate error handling
     */
    @GetMapping("/error")
    public ResponseEntity<ApiResponse<String>> getErrorResponse() {
        throw new RuntimeException("This is a test exception");
    }
}