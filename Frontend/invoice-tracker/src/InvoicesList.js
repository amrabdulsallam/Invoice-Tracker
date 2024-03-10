import InvoiceDetails from './InvoiceDetails';
import axios from 'axios';
import React, { useState } from 'react';
import './InvoicesList.css';
import InvoiceEdit from './InvoiceEdit.js'

function InvoicesList({ invoices }) {
  const [selectedInvoice, setSelectedInvoice] = useState(null);

  const handlePopupClose = () => {
    setSelectedInvoice(null);
  };
  
  const onEditInvoice = (invoice) => {
    setSelectedInvoice(invoice);
  };

  const onInvoiceDelete = async (invoiceId) => {
    try {
      await axios.delete(`http://localhost:8080/api/v1/invoices/${invoiceId}`, {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token'),
          'Content-Type': 'application/json'
        }
      });
    } catch (error) {
      alert("Invoice could not be deleted with id: " + invoiceId);
    }
  };

  return (
    <div>
      {invoices.map((invoice, index) => (
        <InvoiceDetails
          key={index}
          invoice={invoice}
          onDelete={() => onInvoiceDelete(invoice.id)}
          onEdit={() => onEditInvoice(invoice)}
        />
      ))}
        {selectedInvoice && (
        <div className="popup">
          <div className="popup-content">
            <InvoiceEdit invoice = {selectedInvoice} onClose = {handlePopupClose}/>
          </div>
        </div>
      )}
    </div>
  );
}

export default InvoicesList;