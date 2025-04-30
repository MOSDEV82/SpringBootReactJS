import { useState } from 'react';
import reactLogo from './assets/react.svg';
import viteLogo from '/vite.svg';
import './App.css';
import ApiTest from './components/ApiTest';

function App() {
  const [count, setCount] = useState(0);
  const [showApiTest, setShowApiTest] = useState(false);

  return (
    <>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Spring Boot + React + ApiResponse Demo</h1>

      <div className="card">
        <button onClick={() => setShowApiTest(!showApiTest)}>
          {showApiTest ? 'Hide API Test' : 'Show API Test'}
        </button>
        <button onClick={() => setCount((count) => count + 1)} style={{ marginLeft: '10px' }}>
          count is {count}
        </button>
      </div>

      {showApiTest && (
        <div className="api-test-container">
          <ApiTest />
        </div>
      )}

      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  );
}

export default App;
