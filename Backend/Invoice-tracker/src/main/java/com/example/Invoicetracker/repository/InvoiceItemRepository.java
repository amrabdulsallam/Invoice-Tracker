package com.example.Invoicetracker.repository;

import com.example.Invoicetracker.model.Invoice;
import com.example.Invoicetracker.model.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem , Long> {

    List<InvoiceItem> findByInvoice(Invoice invoice);

}
