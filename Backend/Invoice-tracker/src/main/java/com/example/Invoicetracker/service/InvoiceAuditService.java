package com.example.Invoicetracker.service;

import com.example.Invoicetracker.model.Invoice;
import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.model.enums.Action;
import com.example.Invoicetracker.service.dto.InvoiceAuditDTO;

import java.util.List;

public interface InvoiceAuditService {

    void saveInvoiceAudit(User user, Invoice invoice, Action action);

    List<InvoiceAuditDTO> getAllAuditsByUser(long userId);

    List<InvoiceAuditDTO> getAllAuditsByInvoice(long invoiceId);

}
