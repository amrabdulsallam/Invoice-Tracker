package com.example.Invoicetracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "item")
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToMany(mappedBy = "items")
    private List<Invoice> invoices;

    @Override
    public String toString() {
        return "Item Name: " + this.name + " , " + "Quantity: " + this.quantity + " , " + "Price Per Unit: " + this.price;
    }

}
