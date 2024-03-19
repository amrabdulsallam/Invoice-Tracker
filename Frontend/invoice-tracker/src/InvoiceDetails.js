import './InvoiceDetails.css';
import React from 'react';

function InvoiceDetails({ invoice , onDelete , onEdit }) {
  return (
    <div className="invoice-details">
      <div className="header">
        <h2>Invoice Details</h2>
        <div className="invoice-actions">

        </div>
        <div className="invoice-info">
          <p><strong>Invoice Number:</strong> {invoice.invoiceNumber}</p>
          <p><strong>Client Name:</strong> {invoice.clientName}</p>
        </div>
      </div>
      <div className="details">
        <p><strong>Total Amount:</strong> ${invoice.totalAmount}</p>
        <h3>Items:</h3>
        <ul>
          {invoice.invoiceItems.map((item, index) => (
            <li key={index}>
              <strong>Name:</strong> {item.item['name']}, <strong>Quantity:</strong> {item.quantity}, <strong>Price:</strong> ${item.item['price']}
            </li>
          ))}
        </ul>
        <button onClick={() => onDelete(invoice.id)}>Delete</button>
          <button onClick={() => onEdit(invoice)}>Edit</button>
      </div>
    </div>
  );
}

export default InvoiceDetails;
