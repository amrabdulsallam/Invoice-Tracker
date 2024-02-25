import React, { useState } from 'react'
import './App.css';

const Form = () => {
    const [fileName, setFileName] = useState('')
    const [file, setFile] = useState()
    const [fileNameError, setFileNameError] = useState('')
  
    const handleChange = (event) => {
        setFile(event.target.files[0])
        alert("Invoice uploaded")
    }

    const onButtonClick = () => {
        
       setFileNameError('')
           
      if ('' === fileName) {
        setFileNameError('Please enter file name')
        return
      }
      alert(file)
    }

    return (
        <form>
        <div className={'mainContainer'}>
        <div className={'titleContainer'}>
          <div>Upload - Invoice</div>
        </div>
        <br />
        <div className={'inputContainer'}>
          <input
            value={fileName}
            placeholder="Enter your File Name here"
            onChange={(ev) => setFileName(ev.target.value)}
            className={'inputBox'}
          />
        </div>
        <br />
        <div className={'inputContainer'}>
          <input
            type='file'
            onChange={handleChange}
          />
          <label className="errorLabel">{fileNameError}</label>
        </div>
        <br />
        <div className={'inputContainer'}>
          <input className={'inputButton'} type="button" onClick={onButtonClick} value={'Upload Invoice'} />
        </div>
      </div>
      </form>
      );
}

export default Form;