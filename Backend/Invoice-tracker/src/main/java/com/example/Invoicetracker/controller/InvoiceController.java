package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.service.InvoiceService;
import com.example.Invoicetracker.service.dto.InvoiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        try {
            logger.info("Attempt to save invoice with info : " + invoice);
            return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.saveInvoice(invoice));
        } catch (Exception e) {
            logger.error("Failed to save invoice : {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invoice failed to upload");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable long id) {
        try {
            logger.info("Attempt to get invoice with id : " + id);
            return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getInvoiceById(id));
        } catch (Exception e) {
            logger.error("Failed to get invoice : {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invoice not found for Id " + id);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllInvoices() {
        logger.info("Attempt to get all invoices");
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getInvoices());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInvoice(@RequestBody InvoiceDTO invoice, @PathVariable long id) {
        try {
            logger.info("Attempt to update invoice with id : " + id);
            return ResponseEntity.status(HttpStatus.OK).body(invoiceService.updateInvoice(invoice, id));
        } catch (Exception e) {
            logger.error("Failed to update invoice : {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invoice didn't update");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable long id) {
        try {
            logger.info("Attempt to delete invoice with id : " + id);
            invoiceService.deleteInvoice(id);
            logger.info("Invoice deleted with id : " + id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            logger.error("Failed to delete invoice : {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invoice cannot be deleted");
        }
    }

}
