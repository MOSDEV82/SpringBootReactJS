/**
 * TypeScript interface for the standardized API response object.
 * This interface matches the backend ApiResponse class structure.
 */
export interface ApiResponse<T> {
  /**
   * HTTP status code of the response
   */
  code: number;

  /**
   * HTTP method used for the request (GET, POST, PUT, DELETE, etc.)
   */
  method: string;

  /**
   * Timestamp when the response was generated
   */
  timestamp: string;

  /**
   * Flag indicating if the request was successful
   */
  success: boolean;

  /**
   * Message providing additional information about the response
   */
  message: string;

  /**
   * The actual data being returned
   */
  data: T | null;

  /**
   * Metadata as key-value pairs for additional information
   */
  metadata: Record<string, unknown>;
}

/**
 * Type guard to check if a response is an ApiResponse
 */
export function isApiResponse<T>(response: unknown): response is ApiResponse<T> {
  return (
    response &&
    typeof response === 'object' &&
    'code' in response &&
    'method' in response &&
    'timestamp' in response &&
    'success' in response &&
    'message' in response &&
    'data' in response &&
    'metadata' in response
  );
}
