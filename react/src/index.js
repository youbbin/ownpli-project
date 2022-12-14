import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import Container from './Containers/Container'

const container = ReactDOM.createRoot(document.getElementById('app-wrapper'));


container.render(
  <React.StrictMode>
    <Container/>
  </React.StrictMode>
)


