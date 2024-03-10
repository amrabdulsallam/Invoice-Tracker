package com.example.Invoicetracker.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceDTO {

    private long id;

    private String invoiceNumber;

    private String clientName;

    private Double totalAmount;

    private long fileId;

    private long userId;

    private List<ItemDTO> items;

}
