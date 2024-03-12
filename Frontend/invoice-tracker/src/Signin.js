import React, { useState } from 'react'
import axios from 'axios';
import './App.css';
import jwt_decode, { jwtDecode } from 'jwt-decode';


const Signin = (props) => {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [emailError, setEmailError] = useState('')
    const [passwordError, setPasswordError] = useState('')
    const [roles , setRoles] = useState([])

    const OnButtonClick = async (e) => {
      setEmailError('')
      setPasswordError('')
    
      if ('' === email) {
        setEmailError('Please enter your email')
        return
      }
    
      if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email)) {
        setEmailError('Please enter a valid email')
        return
      }
    
      if ('' === password) {
        setPasswordError('Please enter a password')
        return
      }
    
      if (password.length < 2) {
        setPasswordError('The password must be 8 characters or longer')
        return
      }
      e.preventDefault();
      try {
          const response = await axios.post('http://localhost:8080/api/v1/login', {
              email,
              password
          });
          console.log(response.data);
          if(response.data === "Wrong password !" || response.data === "No user found"){
            alert("Invalid user");
          }
          else{
            localStorage.setItem('token', response.data);
            const decodedToken = jwtDecode(response.data)
            setRoles(decodedToken.roles)
            console.log(roles)
            if(decodedToken.roles.includes('ROLE_SUPER_USER')){
              window.location.href = '/admin-page';
            }
            else if(decodedToken.roles.includes('ROLE_AUDITOR')){
              window.location.href = '/invoices';
            }
            else if(decodedToken.roles.includes('ROLE_USER')){
              window.location.href = '/invoice-generator';
            }
            
          }
      } catch (error) {
          alert("Invalid user");
      }
    }
  
    return (
      <div className={'mainContainer'}>
      <div className={'titleContainer'}>
        <div>Invoice Tracker</div>
      </div>
      <br />
      <div className={'inputContainer'}>
        <input
          value={email}
          placeholder="Enter your email here"
          onChange={(ev) => setEmail(ev.target.value)}
          className={'inputBox'}
        />
        <label className="errorLabel">{emailError}</label>
      </div>
      <br />
      <div className={'inputContainer'}>
        <input
          value={password}
          placeholder="Enter your password here"
          onChange={(ev) => setPassword(ev.target.value)}
          className={'inputBox'}
        />
        <label className="errorLabel">{passwordError}</label>
      </div>
      <br />
      <div className={'inputContainer'}>
        <input className={'inputButton'} type="button" onClick={OnButtonClick} value={'Log in'} />
      </div>
    </div>
    );
}

export default Signin;