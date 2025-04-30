import React, { useState } from 'react';
import { ApiResponse } from '../models/ApiResponse';
import { apiFetch, getResponseData, getErrorMessage, getMetadata, isSuccessResponse } from '../utils/apiUtils';
import './ApiTest.css';

interface TestData {
  name: string;
  email: string;
  roles: string[];
}

/**
 * Component to test the ApiResponse implementation
 */
const ApiTest: React.FC = () => {
  const [successResponse, setSuccessResponse] = useState<ApiResponse<string> | null>(null);
  const [dataResponse, setDataResponse] = useState<ApiResponse<TestData> | null>(null);
  const [metadataResponse, setMetadataResponse] = useState<ApiResponse<string> | null>(null);
  const [errorResponse, setErrorResponse] = useState<ApiResponse<string> | null>(null);
  const [loading, setLoading] = useState<boolean>(false);

  // Function to fetch success response
  const fetchSuccessResponse = async () => {
    setLoading(true);
    try {
      const response = await apiFetch<string>('/api/test/success');
      setSuccessResponse(response);
    } catch (error) {
      console.error('Error fetching success response:', error);
    } finally {
      setLoading(false);
    }
  };

  // Function to fetch data response
  const fetchDataResponse = async () => {
    setLoading(true);
    try {
      const response = await apiFetch<TestData>('/api/test/data');
      setDataResponse(response);
    } catch (error) {
      console.error('Error fetching data response:', error);
    } finally {
      setLoading(false);
    }
  };

  // Function to fetch metadata response
  const fetchMetadataResponse = async () => {
    setLoading(true);
    try {
      const response = await apiFetch<string>('/api/test/metadata');
      setMetadataResponse(response);
    } catch (error) {
      console.error('Error fetching metadata response:', error);
    } finally {
      setLoading(false);
    }
  };

  // Function to fetch error response
  const fetchErrorResponse = async () => {
    setLoading(true);
    try {
      const response = await apiFetch<string>('/api/test/error');
      setErrorResponse(response);
    } catch (error) {
      console.error('Error fetching error response:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="api-test">
      <h1>API Response Test</h1>

      <div className="test-section">
        <h2>Success Response</h2>
        <button onClick={fetchSuccessResponse} disabled={loading}>
          Fetch Success Response
        </button>
        {successResponse && (
          <div className="response-display">
            <h3>Response:</h3>
            <pre>{JSON.stringify(successResponse, null, 2)}</pre>
            <h3>Extracted Data:</h3>
            <p>{getResponseData(successResponse)}</p>
          </div>
        )}
      </div>

      <div className="test-section">
        <h2>Data Response</h2>
        <button onClick={fetchDataResponse} disabled={loading}>
          Fetch Data Response
        </button>
        {dataResponse && (
          <div className="response-display">
            <h3>Response:</h3>
            <pre>{JSON.stringify(dataResponse, null, 2)}</pre>
            <h3>Extracted Data:</h3>
            {isSuccessResponse(dataResponse) && dataResponse.data && (
              <div>
                <p>Name: {dataResponse.data.name}</p>
                <p>Email: {dataResponse.data.email}</p>
                <p>Roles: {dataResponse.data.roles.join(', ')}</p>
              </div>
            )}
          </div>
        )}
      </div>

      <div className="test-section">
        <h2>Metadata Response</h2>
        <button onClick={fetchMetadataResponse} disabled={loading}>
          Fetch Metadata Response
        </button>
        {metadataResponse && (
          <div className="response-display">
            <h3>Response:</h3>
            <pre>{JSON.stringify(metadataResponse, null, 2)}</pre>
            <h3>Extracted Metadata:</h3>
            <p>Version: {getMetadata(metadataResponse, 'version')}</p>
            <p>Server: {getMetadata(metadataResponse, 'server')}</p>
            <p>Timestamp: {getMetadata(metadataResponse, 'timestamp')}</p>
          </div>
        )}
      </div>

      <div className="test-section">
        <h2>Error Response</h2>
        <button onClick={fetchErrorResponse} disabled={loading}>
          Fetch Error Response
        </button>
        {errorResponse && (
          <div className="response-display">
            <h3>Response:</h3>
            <pre>{JSON.stringify(errorResponse, null, 2)}</pre>
            <h3>Error Message:</h3>
            <p>{getErrorMessage(errorResponse)}</p>
          </div>
        )}
      </div>

    </div>
  );
};

export default ApiTest;
