import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

// Styled Components
const FormContainer = styled.div`
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  background-color: #f9f9f9;
`;

const Title = styled.h2`
  text-align: center;
  color: #333;
`;

const ErrorMessage = styled.p`
  color: red;
  text-align: center;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
`;

const Label = styled.label`
  margin-bottom: 8px;
  font-weight: bold;
  color: #555;
`;

const Input = styled.input`
  padding: 10px;
  margin-bottom: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
`;

const Button = styled.button`
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;

  &:hover {
    background-color: #0056b3;
  }
`;

const LinkButton = styled.button`
  padding: 10px;
  background-color: transparent;
  color: #007bff;
  border: none;
  font-size: 16px;
  cursor: pointer;
  text-align: center;
  margin-top: 10px;

  &:hover {
    text-decoration: underline;
  }
`;

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    try {
      const response = await fetch('http://localhost:8080/api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        const errorMessage = await response.text(); 
        throw new Error(errorMessage || 'Invalid Authentication Details!');
      }

      const data = await response.text();
      if(!data) throw new Error("Invalid response from the Server");
      const user = JSON.parse(data);
      localStorage.setItem("username", user.username);
      localStorage.setItem("userId", user.userId);

      alert("Login Successful"); 
      navigate('/home'); 
    } catch (err) {
      setError(err.message || 'An error occurred');
    }
  };

  const handleSignUp = () =>{
    navigate('/signup');
  }

  return (
    <FormContainer>
      <Title>Login</Title>
      {error && <ErrorMessage>{error}</ErrorMessage>}
      <Form onSubmit={handleSubmit}> 
        <Label>Username</Label>
        <Input
          id="username"
          type="text"
          value={username}
          pattern="[A-Za-z0-9]{3,20}"
          onChange={(e) => setUsername(e.target.value)} 
          required
        />
        <Label>Password</Label>
        <Input
          type="password"
          value={password}
          minLength={6}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <Button type="submit">Login</Button>
        <LinkButton type="button" onClick={handleSignUp}>Dont have account? SignUp</LinkButton>
      </Form>
    </FormContainer>
  );
}

export default Login; 