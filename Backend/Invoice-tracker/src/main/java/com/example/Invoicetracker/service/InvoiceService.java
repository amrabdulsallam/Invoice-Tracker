package com.example.Invoicetracker.service;

import com.example.Invoicetracker.service.dto.InvoiceDTO;
import com.example.Invoicetracker.service.dto.InvoiceReturnDTO;

import java.util.List;

public interface InvoiceService {

    InvoiceDTO saveInvoice(InvoiceDTO invoice);

    List<InvoiceReturnDTO> getInvoices();

    InvoiceDTO getInvoiceById(long invoiceId);

    InvoiceDTO updateInvoice(InvoiceDTO invoice, long invoiceId);

    void deleteInvoice(long invoiceId);

}
