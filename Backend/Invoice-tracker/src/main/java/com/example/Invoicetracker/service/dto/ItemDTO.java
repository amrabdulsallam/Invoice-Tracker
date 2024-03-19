package com.example.Invoicetracker.service.dto;

import lombok.Data;

@Data
public class ItemDTO {

    private long id;

    private String name;

    private int quantity;

    private double price;

}
