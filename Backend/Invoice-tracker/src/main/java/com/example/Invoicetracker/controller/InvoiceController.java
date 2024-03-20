package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.controller.entity.ResponseEntity;
import com.example.Invoicetracker.service.InvoiceService;
import com.example.Invoicetracker.service.dto.InvoiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/invoices")
public class InvoiceController {

    Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        super();
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<?> saveInvoice(@RequestBody InvoiceDTO invoice) {
        logger.info("Attempt to save invoice with info : " + invoice);
        return new ResponseEntity<>(invoiceService.saveInvoice(invoice));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable long id) {
        logger.info("Attempt to get invoice with id : " + id);
        return new ResponseEntity<>(invoiceService.getInvoiceById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_USER', 'AUDITOR')")
    public ResponseEntity<?> getAllInvoices() {
        logger.info("Attempt to get all invoices");
        return new ResponseEntity<>(invoiceService.getInvoices());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_USER', 'USER')")
    public ResponseEntity<?> updateInvoice(@RequestBody InvoiceDTO invoice, @PathVariable long id) {
        logger.info("Attempt to update invoice with id : " + id);
        return new ResponseEntity<>(invoiceService.updateInvoice(invoice, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_USER', 'USER')")
    public ResponseEntity<?> deleteInvoice(@PathVariable long id) {
        logger.info("Attempt to delete invoice with id : " + id);
        invoiceService.deleteInvoice(id);
        logger.info("Invoice deleted with id : " + id);
        return new ResponseEntity<>();
    }

}
