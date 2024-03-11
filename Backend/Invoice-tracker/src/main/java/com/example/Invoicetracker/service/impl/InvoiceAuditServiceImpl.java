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

    /**
     * Saves an audit record for the invoice action
     * @param user The user performing the action
     * @param invoice The invoice that the user selected to perform an action
     * @param action The action performed such as : (ADDED , EDITED and DELETED)
     */
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

    /**
     * Retrieves all audits performed by a specific user
     * @param userId the id of the user
     * @return List of InvoiceAuditDTO containing details of all audits performed by the user
     * @throws UserNotFoundException If the user with the specified ID is not found
     */
    @Override
    public List<InvoiceAuditDTO> getAllAuditsByUser(long userId) {
        List<InvoiceAudit> userAudits = invoiceAuditRepository.getUserAudits(userId)
                .orElseThrow(() -> new UserNotFoundException("No user found"));

        return invoiceAuditMapper.invoiceAuditsToDtoList(userAudits);
    }

    /**
     * Retrieves all audits performed on a specific invoice
     * @param invoiceId the ID of the invoice
     * @return List of InvoiceAuditDTO containing details of all audits performed on the invoice
     * @throws InvoiceNotFoundException If the invoice with the specified ID is not found
     */
    @Override
    public List<InvoiceAuditDTO> getAllAuditsByInvoice(long invoiceId) {
        List<InvoiceAudit> userAudits = invoiceAuditRepository.getInvoiceAudits(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("No invoice found"));

        return invoiceAuditMapper.invoiceAuditsToDtoList(userAudits);
    }

}
