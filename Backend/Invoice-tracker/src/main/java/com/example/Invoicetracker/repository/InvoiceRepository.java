package com.example.Invoicetracker.repository;

import com.example.Invoicetracker.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
