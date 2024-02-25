import React, { useState } from 'react'
import { Routes, Route } from 'react-router-dom';
import './App.css';
import Signin from './Signin';
import Signup from './Signup';

function App() {
  return (
    <div className="App">
    <Routes>
      <Route path="/" element={<Signin />} />
      <Route path="/signup" element={<Signup />} />
    </Routes>
  </div>
  );
}

export default App;