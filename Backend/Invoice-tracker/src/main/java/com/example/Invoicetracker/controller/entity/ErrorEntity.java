package com.example.Invoicetracker.controller.entity;

import com.example.Invoicetracker.exception.InvoiceTrackerErrorCode;
import com.example.Invoicetracker.model.Invoice;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorEntity {

    private String status;

    private String detail;

    private String error;

    public ErrorEntity(InvoiceTrackerErrorCode code, String message){
        this.status = code.toString();
        this.detail = message;
    }

    public ErrorEntity(InvoiceTrackerErrorCode code, String error , String message){
        this.status = code.toString();
        this.detail = message;
        this.error = error;
    }

}
