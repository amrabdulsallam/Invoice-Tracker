package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.service.InvoiceAuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(InvoiceAuditController.class);

    private final InvoiceAuditService invoiceAuditService;

    @Autowired
    public InvoiceAuditController(InvoiceAuditService invoiceAuditService) {
        super();
        this.invoiceAuditService = invoiceAuditService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getAllAuditsByUser(@PathVariable long userId) {
        try {
            logger.info("Attempt to get all audits by user for id : " + userId);
            return ResponseEntity.status(HttpStatus.OK).body(invoiceAuditService.getAllAuditsByUser(userId));
        } catch (Exception e) {
            logger.error("Failed to get all audits by user : {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/invoices/{invoiceId}")
    public ResponseEntity<?> getAllAuditsByInvoice(@PathVariable long invoiceId) {
        try {
            logger.info("Attempt to get all audits by invoice with id : " + invoiceId);
            return ResponseEntity.status(HttpStatus.OK).body(invoiceAuditService.getAllAuditsByInvoice(invoiceId));
        } catch (Exception e) {
            logger.error("Failed to get all audits by invoice : {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
