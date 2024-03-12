import { useEffect, useState } from "react";
import axios from 'axios';
import jwt_decode, { jwtDecode } from 'jwt-decode';
import InvoicesList from "./InvoicesList";

const AllInvoices = () => {

    const [invoices , setInvoices] = useState([])
    const [role , setRole] = useState([])

    useEffect(() => {

        const token = localStorage.getItem('token')
        if(token === null){
            window.location.href = '/';
        }
        const decodedToken = jwtDecode(token)
        console.log(decodedToken)
        const currentTimestamp = Math.floor(Date.now() / 1000);
        if (decodedToken.exp < currentTimestamp) {
            window.location.href = '/';
        }

        if(decodedToken.roles.includes('ROLE_SUPER_USER') || decodedToken.roles.includes('ROLE_AUDITOR')){
            setRole(decodedToken.role)
        }
        else{
            window.location.href = '/';
        }

        const fetchData = async (e) =>{
            try {
                const response = await axios.get('http://localhost:8080/api/v1/invoices',
                {
                    headers: {
                        'Authorization': 'Bearer '+localStorage.getItem('token'),
                        'Content-Type': 'application/json'
                      }
                });
                console.log(response.data)
                setInvoices(response.data)
            } catch (error) {
                console.log(error);
            }
        }
        fetchData();

    } ,[])


    return(
        <div>
            <InvoicesList invoices={invoices}/>
        </div>
    );
}

export default AllInvoices;