package com.example.Invoicetracker.service.dto;

import com.example.Invoicetracker.model.InvoiceItem;
import lombok.Data;

import java.util.List;

@Data
public class InvoiceReturnDTO {

    private long id;

    private String invoiceNumber;

    private String clientName;

    private Double totalAmount;

    private long fileId;

    private long userId;

    private List<InvoiceItem> invoiceItems;

}
