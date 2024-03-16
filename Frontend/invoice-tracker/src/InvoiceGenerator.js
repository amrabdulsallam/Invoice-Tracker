import React, { useEffect, useState } from 'react';
import BillDetails from './Component/BillDetails';
import ItemList from './Component/ItemList';
import TotalAmount from './Component/TotalAmount';
import { jsPDF } from 'jspdf';
import './InvoiceGenerator.css';
import axios from 'axios';
import jwt_decode, { jwtDecode } from 'jwt-decode';


const InvoiceGenerator = () => {
    const [items, setItems] = useState([]);
    const[fileId ,setFileId] = useState()
    const[clientName ,setClientName] = useState()
    const[invoiceName ,setInvoiceName] = useState('INV-')
    const[userId ,setUserId] = useState()

    const handleAddItem = (item) => {
        setItems([...items, item]);
    };

    useEffect(() => {
        const token = localStorage.getItem('token')
        if(token === null){
            window.location.href = '/';
        }

        const decodedToken = jwtDecode(token)
        setUserId(Number(decodedToken.UserId));
        const currentTimestamp = Math.floor(Date.now() / 1000);
        if (decodedToken.exp < currentTimestamp) {
            window.location.href = '/';
        }
        
    },[])
 
    const handleDeleteItem = (index) => {
        const updatedItems = [...items];
        updatedItems.splice(index, 1);
        setItems(updatedItems);
    };
 
    const calculateTotalAmount = () => {
        return items.reduce(
            (total, item) =>
                total +
                item.quantity *
                item.price, 0);
    };

    const handleInvoiceName = (event) => {
        const value = event.target.value;
        setInvoiceName(value.startsWith('INV-') ? value : `INV-${value}`);
    }
    const handleClientName = (event) => {
        const value = event.target.value;
        setClientName(value)
    }
    const saveInvoice = async (e) =>{
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/v1/invoices', 
            {
                "invoiceNumber" : invoiceName,
                "clientName" : clientName,
                "totalAmount" : calculateTotalAmount(),
                "fileId" : fileId,
                "userId" : Number(userId),
                "items" : items
            },
            {
                headers: {
                    'Authorization': 'Bearer '+localStorage.getItem('token'),
                    'Content-Type': 'application/json'
                  }
            });
            setItems([])
            setClientName()
            setInvoiceName()
            alert("Invoice added")

        } catch (error) {
            alert("Could not upload invoice");
        }
    }

    const uploadFile = async (event) => {
    const file = event.target.files[0];
    const fileName = file.name;
    const formData = new FormData();
    formData.append('fileName', fileName);
    formData.append('file', file);
      try {
          const response = await axios.post('http://localhost:8080/api/v1/files', 
          formData ,
          {
            headers: {
                'Authorization': 'Bearer '+localStorage.getItem('token'),
                'Content-Type': 'multipart/form-data'
              }
          });
          console.log(response.data);
          setFileId(response.data.id);
      } catch (error) {
        console.log(error);
          alert("Coudnlt upload file");
      }
    }

    const handleDownloadPDF = () => {
        const pdf = new jsPDF();
        pdf.text('Invoice', 20, 20);
 
        items.forEach((item, index) => {
            const yPos = 70 + index * 20;
            pdf.text(
                `Item: ${item.item}, 
                    Quantity: ${item.quantity}, 
                    Price: ${item.price}`, 20, yPos);
        });
 
        const totalAmount =
            calculateTotalAmount();
        pdf.text(
            `Total Amount: 
                    $${totalAmount.toFixed(2)}`, 20, 200);
 
        pdf.save(`Invoice-${Math.floor(Math.random()*999999)}.pdf`);
    };
 
    return (
        <div className="App-Style">
            <h1>Invoice Generator : </h1>
            <label>Client Name</label>
            <input type='text' value={clientName} onChange={handleClientName}/>
            <label>Invoice Name</label>
            <input type='text' value={invoiceName} onChange={handleInvoiceName}/>
            <BillDetails onAddItem={handleAddItem} />
            <ItemList items={items}
                onDeleteItem={handleDeleteItem} />
            <TotalAmount
                total={calculateTotalAmount()} />
            <button
                onClick={handleDownloadPDF}>Download PDF</button>

          <input
            type='file'
            onChange={uploadFile}
          />
        <button onClick={saveInvoice}>Save Invoice</button>
        </div>
    );
}
 
export default InvoiceGenerator;