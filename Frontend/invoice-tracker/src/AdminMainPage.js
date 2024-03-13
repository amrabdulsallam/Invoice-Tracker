import React, { useEffect, useState } from 'react';
import './AdminMainPage.css'
import axios from 'axios';
import jwt_decode, { jwtDecode } from 'jwt-decode';
import InvoicesList from './InvoicesList'

const AdminPage = () => {
    const [userInfo, setUserInfo] = useState('');
    const [userInvoices, setUserInvoices] = useState('');
    const [invoiceAudits, setInvoiceAudits] = useState('');
    const [userInfoResponse, setUserInfoResponse] = useState({});
    const [userInvoicesResponse, setUserInvoicesResponse] = useState([]);
    const [invoiceAuditsResponse, seIinvoiceAuditsResponse] = useState([]);
    const [selectedOption, setSelectedOption] = useState('');
    const [userId, setUserId] = useState('');
    const [selectedOption2, setSelectedOption2] = useState('');
    const [userId2, setUserId2] = useState('');

    const handleUserIdChange = (event) => {
      setUserId(event.target.value);
    };
    const handleUserIdChange2= (event) => {
      setUserId2(event.target.value);
    };
    const handleOptionChange = (event) => {
      setSelectedOption(event.target.value);
    };
    const handleOptionChange2 = (event) => {
      setSelectedOption2(event.target.value);
    };

    useEffect(()=> {
      const token = localStorage.getItem('token')
        if(token === null){
            window.location.href = '/';
        }
        const decodedToken = jwtDecode(token)
        const currentTimestamp = Math.floor(Date.now() / 1000);
        if (decodedToken.exp < currentTimestamp) {
            window.location.href = '/';
        }
        if(!decodedToken.roles.includes('ROLE_SUPER_USER')){
          window.location.href = '/';
        }
    },[userInfo,userInvoices,invoiceAudits])

    const handleSubmitUserInfo = async (event) => {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/users/'+userInfo,
        {
            headers: {
                'Authorization': 'Bearer '+localStorage.getItem('token'),
                'Content-Type': 'application/json'
              }
        });
        setUserInfoResponse(response.data)
    } catch (error) {
        alert("No user found")
    }
    }

    const handleSubmitUserInvoice = async (event) => {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/users/'+userInvoices+'/invoices',
        {
            headers: {
                'Authorization': 'Bearer '+localStorage.getItem('token'),
                'Content-Type': 'application/json'
              }
        });
        setUserInvoicesResponse(response.data.content)
        
    } catch (error) {
        alert("No user found")
    }
    }

    const handleInvoiceAudit = async (event) => {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/invoices-audits/invoices/'+invoiceAudits,
        {
            headers: {
                'Authorization': 'Bearer '+localStorage.getItem('token'),
                'Content-Type': 'application/json'
              }
        });
        console.log(response.data)
        seIinvoiceAuditsResponse(response.data)
        if(response.data.length <= 0){
          alert("No audits for this invoice found")
        }
    } catch (error) {
        alert("No audits for this invoice found")
    }
    }

    const handleAssignUserRole = async () => {
      try {
        await axios.post('http://localhost:8080/api/v1/users/'+userId+'/roles/'+selectedOption,
        {},
        {
            headers: {
                'Authorization': 'Bearer '+localStorage.getItem('token'),
                'Content-Type': 'application/json'
              }
        });
        alert("Role Granted")
    } catch (error) {
        alert("Coudln't give the role to the user")
    }
    }

    const handleUserRoleDeletion = async () => {
      try {
        await axios.delete('http://localhost:8080/api/v1/users/'+userId2+'/roles/'+selectedOption2,
        {
            headers: {
                'Authorization': 'Bearer '+localStorage.getItem('token'),
                'Content-Type': 'application/json'
              }
        });
        alert("Role Deleted")
    } catch (error) {
        alert("Coudln't delete the role to the user")
    }
    }

    return(
    <div className="container">
    <div className="form-container">
        <label>User Info:</label>
        <input type="text" value={userInfo} onChange={(e) => setUserInfo(e.target.value)} />
        <button onClick={handleSubmitUserInfo}>submit</button>
        <p className="response">Id : {userInfoResponse.id}</p>
        <p className="response">Email : {userInfoResponse.email}</p>
        <p className="response">Phone : {userInfoResponse.phone}</p>
        <p className="response">SignupDate : {userInfoResponse.signupDate}</p>
        <ul>
        {userInfoResponse.roles && userInfoResponse.roles.map((role, index) => (
          <li key={index}>{role.role}</li>
        ))}
      </ul>
    </div>
    
    <div className="form-container">
        <label>User Invoices:</label>
        <input type="text" value={userInvoices} onChange={(e) => setUserInvoices(e.target.value)} />
        <button onClick={handleSubmitUserInvoice}>Submit</button>
        {userInvoicesResponse && <InvoicesList invoices={userInvoicesResponse}/>}
    </div>
    
    <div className="form-container">
        <label>Invoice Audit:</label>
        <input type="text" value={invoiceAudits} onChange={(e) => setInvoiceAudits(e.target.value)} />
        <button onClick={handleInvoiceAudit}>Submit</button>
        <p className="response">Invoice Audit for ID - {invoiceAudits}</p>
        {invoiceAuditsResponse && invoiceAuditsResponse.map((data, index) => (
          <div key={index}>
              <p className="response">Audit Date : {data.auditDate}</p>
              <p className="response">Old Value : {data.oldValue}</p>
              <p className="response">Action : {data.action}</p>
          </div>
        ))}
    </div>
    <div className="form-container">
    <label>
        Assign Role To User ID:
        <input type="text" value={userId} onChange={handleUserIdChange} />
      </label>
      <br />
      <label>
        Super User
        <input
          type="radio"
          value="SUPER_USER"
          checked={selectedOption === "SUPER_USER"}
          onChange={handleOptionChange}
        />
      </label>
      <br />
      <label>
        User
        <input
          type="radio"
          value="USER"
          checked={selectedOption === "USER"}
          onChange={handleOptionChange}
        />
      </label>
      <br />
      <label>
        Auditor
        <input
          type="radio"
          value="AUDITOR"
          checked={selectedOption === "AUDITOR"}
          onChange={handleOptionChange}
        />
      </label>
      <br />
      <button onClick={handleAssignUserRole}>Submit</button>
    </div>
    <div className="form-container">
    <label>
        Remove Role From User ID:
        <input type="text" value={userId2} onChange={handleUserIdChange2} />
      </label>
      <br />
      <label>
        Super User
        <input
          type="radio"
          value="SUPER_USER"
          checked={selectedOption2 === "SUPER_USER"}
          onChange={handleOptionChange2}
        />
      </label>
      <br />
      <label>
        User
        <input
          type="radio"
          value="USER"
          checked={selectedOption2 === "USER"}
          onChange={handleOptionChange2}
        />
      </label>
      <br />
      <label>
        Auditor
        <input
          type="radio"
          value="AUDITOR"
          checked={selectedOption2 === "AUDITOR"}
          onChange={handleOptionChange2}
        />
      </label>
      <br />
      <button onClick={handleUserRoleDeletion}>Submit</button>
    </div>
    </div>
    );
}

export default AdminPage;