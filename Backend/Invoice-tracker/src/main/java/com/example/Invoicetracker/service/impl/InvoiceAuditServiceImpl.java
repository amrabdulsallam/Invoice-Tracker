package com.example.Invoicetracker.service.impl;

import com.example.Invoicetracker.exception.InvoiceNotFoundException;
import com.example.Invoicetracker.exception.UserNotFoundException;
import com.example.Invoicetracker.model.Invoice;
import com.example.Invoicetracker.model.InvoiceAudit;
import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.model.enums.Action;
import com.example.Invoicetracker.repository.InvoiceAuditRepository;
import com.example.Invoicetracker.service.InvoiceAuditService;
import com.example.Invoicetracker.service.dto.InvoiceAuditDTO;
import com.example.Invoicetracker.service.mapper.InvoiceAuditMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceAuditServiceImpl implements InvoiceAuditService {

    private final InvoiceAuditRepository invoiceAuditRepository;
    private final InvoiceAuditMapper invoiceAuditMapper;

    @Autowired
    public InvoiceAuditServiceImpl(InvoiceAuditRepository invoiceAuditRepository, InvoiceAuditMapper invoiceAuditMapper) {
        super();
        this.invoiceAuditRepository = invoiceAuditRepository;
        this.invoiceAuditMapper = invoiceAuditMapper;
    }

    @Override
    public void saveInvoiceAudit(User user, Invoice invoice, Action action) {
        InvoiceAudit invoiceAudit = new InvoiceAudit();

        invoiceAudit.setUserAudit(user);
        invoiceAudit.setAction(action);
        invoiceAudit.setInvoiceAudit(invoice);

        if (action != Action.ADDED) {
            invoiceAudit.setOldValue(invoice.toString());
        } else {
            invoiceAudit.setOldValue("NEW");
        }

        invoiceAuditRepository.save(invoiceAudit);
    }

    @Override
    public List<InvoiceAuditDTO> getAllAuditsByUser(long userId) {
        List<InvoiceAudit> userAudits = invoiceAuditRepository.getUserAudits(userId)
                .orElseThrow(() -> new UserNotFoundException("No user found"));

        return invoiceAuditMapper.invoiceAuditsToDtoList(userAudits);
    }

    @Override
    public List<InvoiceAuditDTO> getAllAuditsByInvoice(long invoiceId) {
        List<InvoiceAudit> userAudits = invoiceAuditRepository.getInvoiceAudits(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("No invoice found"));

        return invoiceAuditMapper.invoiceAuditsToDtoList(userAudits);
    }

}
