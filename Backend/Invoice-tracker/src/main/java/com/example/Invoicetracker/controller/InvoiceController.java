package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.service.InvoiceService;
import com.example.Invoicetracker.service.dto.InvoiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        super();
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<?> saveInvoice(@RequestBody InvoiceDTO invoice) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.saveInvoice(invoice));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invoice failed to upload");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getInvoiceById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invoice not found for Id " + id);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllInvoices() {
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getInvoices());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInvoice(@RequestBody InvoiceDTO invoice, @PathVariable long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(invoiceService.updateInvoice(invoice, id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invoice didn't update");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable long id) {
        try {
            invoiceService.deleteInvoice(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invoice cannot be deleted");
        }
    }

}
