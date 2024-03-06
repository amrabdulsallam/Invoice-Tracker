package com.example.Invoicetracker.service.dto;

import com.example.Invoicetracker.model.enums.Action;
import lombok.Data;

import java.util.Date;

@Data
public class InvoiceAuditDTO {

    private long id;

    private Action action;

    private Date auditDate;

    private String oldValue;

    private long invoiceId;

    private long userId;

}
