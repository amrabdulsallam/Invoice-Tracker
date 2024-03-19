import React from 'react';
 
const ItemListEdit = ({ items, onDeleteItem }) => {
    console.log("----")
    console.log(items)
    console.log("----")
    return (
        <div className="item-list">
            <h2>Item List</h2>
            {items.map((item, index) => (
                <div className="item" key={index}>
                    <div>{item.item['name']}</div>
                    <div>
                        Quantity:
                        {item.quantity}
                    </div>
                    <div>Price: ${Number(item.item['price'])}</div>
                    <button onClick={
                        () =>
                            onDeleteItem(index)}>
                        Delete
                    </button>
                </div>
            ))}
        </div>
    );
};
 
export default ItemListEdit;