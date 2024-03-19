import './App.css';
import { useEffect, useState } from "react";
import axios from 'axios';
import jwt_decode, { jwtDecode } from 'jwt-decode';
import InvoicesList from './InvoicesList';

const Dashboard = () => {
    const [page , setPage] = useState(0)
    const [userId ,setUserId] = useState();
    const [invoices , setInvoices] = useState([])
    const [search , setSearch] = useState('')

    useEffect(()=> {
        const token = localStorage.getItem('token')
        if(token === null){
            window.location.href = '/';
        }
        const decodedToken = jwtDecode(token)
        console.log(decodedToken)
        setUserId(decodedToken.UserId);
        const currentTimestamp = Math.floor(Date.now() / 1000);
        if (decodedToken.exp < currentTimestamp) {
            window.location.href = '/';
        }

        const fetchData = async (e) =>{
            try {
                const response = await axios.get('http://localhost:8080/api/v1/users/'+userId+'/invoices',
                {
                    params : {
                        page : page,
                        size : 3
                    },
                    headers: {
                        'Authorization': 'Bearer '+localStorage.getItem('token'),
                        'Content-Type': 'application/json'
                      }
                });
                console.log(response.data.content)
                setInvoices(response.data.content)
            } catch (error) {
                console.log(error);
            }
        }

        fetchData();
        
    },[userId,page])
    const HandlePrePage = () => {
        if(page >= 1){
            setPage(page - 1);
        }
    }

    const HandleNextPage = () => {
        setPage(page + 1)
    }

    const handleSearch = async (e) => {
        try {
            const response = await axios.get('http://localhost:8080/api/v1/users/'+userId+'/invoices',
            {
                params : {
                    page : page,
                    size : 3,
                    search : search
                },
                headers: {
                    'Authorization': 'Bearer '+localStorage.getItem('token'),
                    'Content-Type': 'application/json'
                  }
            });
            console.log(response.data.content)
            setInvoices(response.data.content)
        } catch (error) {
            console.log(error);
        }
    }

    return(
        <div>
            <h1>Dashboard</h1>
            <label>Search for invoice : </label>
            <br/>
            <input value={search} onChange={(e) => setSearch(e.target.value)}/>
            <br/>
            <button onClick={handleSearch}>Submit</button>
            <InvoicesList invoices={invoices} />
            <footer className="pages">
                <button onClick={HandlePrePage}>Prev</button>
                <p>{page}</p>
                <button onClick={HandleNextPage}>Next</button>
            </footer>
        </div>
    );
}

export default Dashboard;