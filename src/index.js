import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter as Router } from 'react-router-dom'
import './index.css';
import App from './App';
import { AppProvider } from './context/AppContext';
import { GoogleOAuthProvider } from 'react-oauth-google';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
<GoogleOAuthProvider clientId="940988695510-6if6nnnq6k6chc6u2n60et060p26mbbt.apps.googleusercontent.com">
  <React.StrictMode>
    <AppProvider>
    <Router>
    <App />
    </Router>
    </AppProvider>
  </React.StrictMode>
  </GoogleOAuthProvider>
);
