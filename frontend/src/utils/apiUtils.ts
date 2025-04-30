import { ApiResponse } from '../models/ApiResponse';

/**
 * Utility functions for working with ApiResponse objects
 */

/**
 * Check if the API response was successful
 * @param response The API response to check
 * @returns True if the response was successful, false otherwise
 */
export const isSuccessResponse = <T>(response: ApiResponse<T>): boolean => {
  return response.success && response.code >= 200 && response.code < 300;
};

/**
 * Check if the API response was an error
 * @param response The API response to check
 * @returns True if the response was an error, false otherwise
 */
export const isErrorResponse = <T>(response: ApiResponse<T>): boolean => {
  return !response.success || response.code < 200 || response.code >= 300;
};

/**
 * Get the data from an API response, or null if the response was an error
 * @param response The API response to extract data from
 * @returns The data from the response, or null if the response was an error
 */
export const getResponseData = <T>(response: ApiResponse<T>): T | null => {
  return isSuccessResponse(response) ? response.data : null;
};

/**
 * Get the error message from an API response
 * @param response The API response to extract the error message from
 * @returns The error message from the response, or a default message if none was provided
 */
export const getErrorMessage = <T>(response: ApiResponse<T>): string => {
  return response.message || 'An unknown error occurred';
};

/**
 * Get a specific metadata value from an API response
 * @param response The API response to extract metadata from
 * @param key The metadata key to extract
 * @returns The metadata value, or undefined if not found
 */
export const getMetadata = <T, K extends keyof Record<string, unknown>>(
  response: ApiResponse<T>,
  key: K
): unknown => {
  return response.metadata[key as string];
};

/**
 * Create a fetch wrapper that automatically converts responses to ApiResponse objects
 * @returns A fetch function that returns ApiResponse objects
 */
export const createApiFetch = () => {
  return async <T>(url: string, options?: RequestInit): Promise<ApiResponse<T>> => {
    try {
      const response = await fetch(url, options);
      const data = await response.json();
      return data as ApiResponse<T>;
    } catch (error) {
      // If the fetch fails, create an error response
      return {
        code: 500,
        method: options?.method || 'GET',
        timestamp: new Date().toISOString(),
        success: false,
        message: error instanceof Error ? error.message : 'Network error',
        data: null,
        metadata: {}
      };
    }
  };
};

// Create and export a default apiFetch instance
export const apiFetch = createApiFetch();