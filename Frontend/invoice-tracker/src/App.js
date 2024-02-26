import React, { useState } from 'react'
import { Routes, Route } from 'react-router-dom';
import './App.css';
import Signin from './Signin';
import Signup from './Signup';
import Form from './Form';
import Dashboard from './Dashboard';


function App() {
  return (
    <div className="App">
    <Routes>
      <Route path="/" element={<Signin />} />
      <Route path="/signup" element={<Signup />} />
      <Route path="/form" element={<Form />} />
      <Route path="/dashboard" element={<Dashboard />} />
    </Routes>
  </div>
  );
}

export default App;