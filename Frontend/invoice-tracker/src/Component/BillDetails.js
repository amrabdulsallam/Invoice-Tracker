import React, { useState } from 'react';
 
const BillDetails = ({ onAddItem, onDeleteItem }) => {
    const [name, setItem] = useState('');
    const [quantity, setQuantity] = useState(1);
    const [price, setPrice] = useState(1);
    const [errorMessage, setErrorMessage] = useState('');
 
    const handleAddItem = () => {
        if (!name.trim()) {
            setErrorMessage(`Please input data in the Item section.`);
            return;
        }
        
        const newItem = { name, quantity, price };
        onAddItem(newItem);
        setItem('');
        setQuantity(1);
        setPrice(0);
        setErrorMessage('');
    };
 
    return (
        <div>
            <label>Item:</label>
            <input type="text"
                value={name}
                onChange={
                    (e) =>
                        setItem(e.target.value)} />
            <label>Quantity:</label>
            <input type="number"
                value={quantity}
                onChange={
                    (e) =>
                        setQuantity(e.target.value)} />
            <label>Price:</label>
            <input type="number"
                value={price}
                onChange={
                    (e) =>
                        setPrice(parseFloat(e.target.value))} />
            <button
                onClick={handleAddItem}>
                Add Item
            </button>
            <p style={{ color: 'red' }}>{errorMessage}</p>
 
        </div>
    );
};
 
export default BillDetails;