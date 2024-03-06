package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.service.InvoiceAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/invoices-audits")
public class InvoiceAuditController {

    private final InvoiceAuditService invoiceAuditService;

    @Autowired
    public InvoiceAuditController(InvoiceAuditService invoiceAuditService) {
        super();
        this.invoiceAuditService = invoiceAuditService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getAllAuditsByUser(@PathVariable long userId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(invoiceAuditService.getAllAuditsByUser(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/invoices/{invoiceId}")
    public ResponseEntity<?> getAllAuditsByInvoice(@PathVariable long invoiceId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(invoiceAuditService.getAllAuditsByInvoice(invoiceId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
