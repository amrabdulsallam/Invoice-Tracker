package com.example.Invoicetracker.service.impl;

import com.example.Invoicetracker.exception.InvoiceNotFoundException;
import com.example.Invoicetracker.exception.UserNotFoundException;
import com.example.Invoicetracker.model.*;
import com.example.Invoicetracker.model.enums.Action;
import com.example.Invoicetracker.repository.*;
import com.example.Invoicetracker.service.InvoiceAuditService;
import com.example.Invoicetracker.service.InvoiceService;
import com.example.Invoicetracker.service.dto.InvoiceDTO;
import com.example.Invoicetracker.service.dto.InvoiceReturnDTO;
import com.example.Invoicetracker.service.dto.ItemDTO;
import com.example.Invoicetracker.service.mapper.InvoiceMapper;
import com.example.Invoicetracker.service.mapper.ItemMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final InvoiceMapper invoiceMapper;
    private final ItemMapper itemMapper;
    private final InvoiceAuditService invoiceAuditService;
    private final InvoiceItemRepository invoiceItemRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, UserRepository userRepository, InvoiceMapper invoiceMapper, FileRepository fileRepository, ItemMapper itemMapper, InvoiceAuditService invoiceAuditService, InvoiceItemRepository invoiceItemRepository, ItemRepository itemRepository) {
        super();
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
        this.invoiceMapper = invoiceMapper;
        this.fileRepository = fileRepository;
        this.itemMapper = itemMapper;
        this.invoiceAuditService = invoiceAuditService;
        this.invoiceItemRepository = invoiceItemRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * Saves an invoice to DB
     *
     * @param invoiceDto The invoiceDTO to be saved
     * @return The saved invoice in the DB
     * @throws UserNotFoundException    If the user with the specified ID  not found
     * @throws InvoiceNotFoundException If the file with the specified ID  not found
     */
    @Override
    @Transactional
    public InvoiceDTO saveInvoice(InvoiceDTO invoiceDto) {
        User user = userRepository.findById(invoiceDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found for the id : " + invoiceDto.getUserId()));

        File file = fileRepository.findById(invoiceDto.getFileId()).orElse(null);

        Invoice invoice = invoiceMapper.dtoToInvoice(invoiceDto);
        invoice.setUserInvoice(user);
        invoice.setInvoiceFile(file);

        invoiceRepository.save(invoice);

        List<InvoiceItem> invoiceItems = new ArrayList<>();
        for (ItemDTO item : invoiceDto.getItems()) {
            Item existingItem = itemRepository.findByName(item.getName());

            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setItem(existingItem);
            invoiceItem.setQuantity(item.getQuantity());
            invoiceItem.setInvoice(invoice);
            invoiceItems.add(invoiceItem);
        }
        invoiceItemRepository.saveAll(invoiceItems);

        invoice.setInvoiceItems(invoiceItems);

        invoiceRepository.save(invoice);

        invoiceAuditService.saveInvoiceAudit(user, invoice, Action.ADDED);

        return invoiceDto;
    }

    /**
     * Retrieves all invoices
     *
     * @return List of InvoiceReturnDTO containing details of all invoices
     */
    @Override
    public List<InvoiceReturnDTO> getInvoices() {
        List<Invoice> test = invoiceRepository.findAll();
        return invoiceMapper.invoicesToInvoiceReturnDto(test);
    }

    /**
     * Retrieves an invoice by its ID
     *
     * @param invoiceId The ID of the invoice to retrieve
     * @return The invoice DTO containing details of the retrieved invoice
     * @throws InvoiceNotFoundException If the invoice with the specified ID is not found
     */
    @Override
    public InvoiceDTO getInvoiceById(long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found"));
        return invoiceMapper.invoiceToDto(invoice);
    }

    /**
     * Updates an existing invoice
     *
     * @param newInvoice The updated invoice DTO
     * @param invoiceId  The ID of the invoice to update
     * @return The updated invoice DTO
     * @throws InvoiceNotFoundException If the invoice with the specified ID is not found
     * @throws UserNotFoundException    If the user with the specified ID is not found
     */
    @Override
    @Transactional
    public InvoiceDTO updateInvoice(InvoiceDTO newInvoice, long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found"));

        User user = userRepository.findById(newInvoice.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        invoiceAuditService.saveInvoiceAudit(user, invoice, Action.EDITED);

        List<InvoiceItem> existingItems = invoiceItemRepository.findByInvoice(invoice);
        invoiceItemRepository.deleteAll(existingItems);

        List<InvoiceItem> invoiceItems = new ArrayList<>();
        for (ItemDTO item : newInvoice.getItems()) {
            Item existingItem = itemRepository.findByName(item.getName());

            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setItem(existingItem);
            invoiceItem.setQuantity(item.getQuantity());
            invoiceItem.setInvoice(invoice);
            invoiceItems.add(invoiceItem);
        }
        invoiceItemRepository.saveAll(invoiceItems);


        invoice.setClientName(newInvoice.getClientName());
        invoice.setTotalAmount(newInvoice.getTotalAmount());
        invoice.setInvoiceNumber(newInvoice.getInvoiceNumber());

        invoiceRepository.save(invoice);

        return invoiceMapper.invoiceToDto(invoice);
    }

    /**
     * Deletes an invoice by its ID
     *
     * @param invoiceId The ID of the invoice to delete
     * @throws InvoiceNotFoundException If the invoice with the specified ID is not found
     */
    @Override
    @Transactional
    public void deleteInvoice(long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found"));

        invoiceRepository.softDelete(invoice.getId());

        invoiceAuditService.saveInvoiceAudit(invoice.getUserInvoice(), invoice, Action.DELETED);
    }

}
