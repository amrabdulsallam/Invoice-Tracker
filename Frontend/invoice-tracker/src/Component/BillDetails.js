import React, { useState, useEffect } from 'react';
import axios from 'axios';

const BillDetails = ({ onAddItem, onDeleteItem }) => {
    const [name, setItem] = useState('');
    const [quantity, setQuantity] = useState(1);
    const [price, setPrice] = useState();
    const [errorMessage, setErrorMessage] = useState('');
    const [items, setItems] = useState([]);
    const [selectedItem, setSelectedItem] = useState();

    useEffect(() => {
      const fetchData = async (e) =>{
          try {
              const response = await axios.get('http://localhost:8080/api/v1/items',
              {
                  headers: {
                      'Authorization': 'Bearer '+localStorage.getItem('token'),
                      'Content-Type': 'application/json'
                    }
              });
              console.log(response.data)
              setItems(response.data)
              setPrice(response.data[0].price)
              setItem(response.data[0].name)
          } catch (error) {
              console.log(error);
          }
      }
  
      fetchData();
    }, []);
  
    const handleChange = (event) => {
      setSelectedItem(event.target.value);
      var id = event.target.value - 1;
      setPrice(items[id].price)
      setItem(items[id].name)
    };
    
    const handleAddItem = () => {
        const newItem = { name, quantity, price };
        onAddItem(newItem);
        setQuantity(1);
        setErrorMessage('');
    };
 
    return (
        <div>
            <label>Select an item:</label>
            <select value={selectedItem} onChange={handleChange}>
                    {items.map(item => (
                        <option key={item.id} value={item.id}>{item.name}</option>
                ))}
            </select>
            <label>Quantity:</label>
            <input type="number"
                value={quantity}
                onChange={
                    (e) =>
                        setQuantity(e.target.value)} />
            <label>Price: {price} $</label>
            <button
                onClick={handleAddItem}>
                Add Item
            </button>
            <p style={{ color: 'red' }}>{errorMessage}</p>
        </div>
    );
};
 
export default BillDetails;