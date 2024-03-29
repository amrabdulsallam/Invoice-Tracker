import React, {useState } from 'react';
import BillDetails from './Component/BillDetails';
import ItemList from './Component/ItemList';
import TotalAmount from './Component/TotalAmount';
import './InvoiceGenerator.css';
import axios from 'axios';
import ItemListEdit from './Component/ItemListEdit';


const InvoiceGenerator = ({invoice , onClose}) => {
    const [items, setItems] = useState(invoice.invoiceItems);
    const[fileId ,setFileId] = useState()
    const[clientName ,setClientName] = useState(invoice.clientName)
    const[invoiceName ,setInvoiceName] = useState(invoice.invoiceNumber)
    const[userId ,setUserId] = useState(invoice.userId)


    const editInvoice = async () => {
        const newItems = items.map(item => {
            return {
                id : item.item.id,
                name : item.item.name,
                price : item.item.price,
                quantity : item.quantity
            }
        })
        console.log(newItems);
        try {
            const response = await axios.put('http://localhost:8080/api/v1/invoices/'+invoice.id, 
            {
                "invoiceNumber" : invoiceName,
                "clientName" : clientName,
                "totalAmount" : calculateTotalAmount(),
                "fileId" : fileId,
                "userId" : Number(userId),
                "items" : newItems
            },
            {
                headers: {
                    'Authorization': 'Bearer '+localStorage.getItem('token'),
                    'Content-Type': 'application/json'
                  }
            });
            console.log(response.data);

        } catch (error) {
            alert("Could not upload invoice");
        }
    }

    const handleAddItem = (item) => {
        const fixedObject =  {
            item : {
                id : item.id,
                name : item.name,
                price : item.price
            },
            quantity : item.quantity
        }
       console.log(fixedObject)
        setItems([...items, fixedObject]);
    };
 
    const handleDeleteItem = (index) => {
        const updatedItems = [...items];
        updatedItems.splice(index, 1);
        setItems(updatedItems);
    };
 
    const calculateTotalAmount = () => {
        console.log(items);
        return items.reduce(
            (total, item) =>
                total + item.quantity * item.item.price, 0);
    };

    const handleInvoiceName = (event) => {
        const value = event.target.value;
        setInvoiceName(value.startsWith('INV-') ? value : `INV-${value}`);
    }
    const handleClientName = (event) => {
        const value = event.target.value;
        setClientName(value)
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
 
    return (
        <div className="App-Style">
            <button className="close" onClick={onClose}>
             &times;
            </button>
            <h1>Edit Invoice : </h1>
            <label>Client Name</label>
            <input type='text' value={clientName} onChange={handleClientName}/>
            <label>Invoice Name</label>
            <input type='text' value={invoiceName} onChange={handleInvoiceName}/>
            <BillDetails onAddItem={handleAddItem} />
            <ItemListEdit items={items}
                onDeleteItem={handleDeleteItem} />
            <TotalAmount
                total={calculateTotalAmount()} />

          <input
            type='file'
            onChange={uploadFile}
          />
          <button onClick={editInvoice}>
            Save
          </button>
        </div>
    );
}
 
export default InvoiceGenerator;