import React, { useState } from 'react'
import './App.css';

const Signup = (props) => {
    const [email, setEmail] = useState('')
    const [name, setName] = useState('')
    const [emailError, setEmailError] = useState('')
    const [nameError, setNameError] = useState('')
    const [password, setPassword] = useState('')
    const [passwordError, setPasswordError] = useState('')
    const [phone, setPhone] = useState('')
    const [phoneError, setPhoneError] = useState('')
  
    const onButtonClick = () => {
      setEmailError('')
      setNameError('')
      setPasswordError('')
      setPhoneError('')
    
      // Check if the user has entered both fields correctly
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

      if (phone.length < 7) {
        setPhoneError('Phone must be 8 numbers or longer')
        return
      }
    
      if (password.length < 7) {
        setPasswordError('The password must be 8 characters or longer')
        return
      }

    
      alert(name+"\n"+email+"\n"+password+"\n"+phone)
    }

    return (
        <div className={'mainContainer'}>
        <div className={'titleContainer'}>
          <div>Sign Up - Invoice Tracker</div>
        </div>
        <br />
        <div className={'inputContainer'}>
          <input
            value={name}
            placeholder="Enter your Name here"
            onChange={(ev) => setName(ev.target.value)}
            className={'inputBox'}
          />
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
            value={phone}
            placeholder="Enter your phone here"
            onChange={(ev) => setPhone(ev.target.value)}
            className={'inputBox'}
          />
          <label className="errorLabel">{phoneError}</label>
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
          <input className={'inputButton'} type="button" onClick={onButtonClick} value={'Sign Up'} />
        </div>
      </div>
      );
}

export default Signup;