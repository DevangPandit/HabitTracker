import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SignUp from './components/signup/signup';
import Login from './components/login/login';
import Home from './components/dashboard/home';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path='/home' element={<Home />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
