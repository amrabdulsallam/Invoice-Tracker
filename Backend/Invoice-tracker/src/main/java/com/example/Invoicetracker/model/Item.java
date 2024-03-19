package com.example.Invoicetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "price", nullable = false)
    private Double price;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private List<InvoiceItem> invoiceItems;

    @Override
    public String toString() {
        return "Item Name: " + this.name + " , " + "Price Per Unit: " + this.price;
    }

}
