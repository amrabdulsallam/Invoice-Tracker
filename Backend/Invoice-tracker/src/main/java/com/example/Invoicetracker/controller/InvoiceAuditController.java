package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.controller.entity.ResponseEntity;
import com.example.Invoicetracker.service.InvoiceAuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('SUPER_USER')")
    public ResponseEntity<?> getAllAuditsByUser(@PathVariable long userId) {
        logger.info("Attempt to get all audits by user for id : " + userId);
        return new ResponseEntity<>(invoiceAuditService.getAllAuditsByUser(userId));
    }

    @GetMapping("/invoices/{invoiceId}")
    @PreAuthorize("hasRole('SUPER_USER')")
    public ResponseEntity<?> getAllAuditsByInvoice(@PathVariable long invoiceId) {
        logger.info("Attempt to get all audits by invoice with id : " + invoiceId);
        return new ResponseEntity<>(invoiceAuditService.getAllAuditsByInvoice(invoiceId));
    }

}
