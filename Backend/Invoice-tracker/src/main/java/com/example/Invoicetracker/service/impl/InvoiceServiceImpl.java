package com.example.Invoicetracker.service.impl;

import com.example.Invoicetracker.exception.InvoiceNotFoundException;
import com.example.Invoicetracker.exception.UserNotFoundException;
import com.example.Invoicetracker.model.File;
import com.example.Invoicetracker.model.Invoice;
import com.example.Invoicetracker.model.Item;
import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.repository.FileRepository;
import com.example.Invoicetracker.repository.InvoiceRepository;
import com.example.Invoicetracker.repository.UserRepository;
import com.example.Invoicetracker.service.InvoiceService;
import com.example.Invoicetracker.service.dto.InvoiceDTO;
import com.example.Invoicetracker.service.mapper.InvoiceMapper;
import com.example.Invoicetracker.service.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final InvoiceMapper invoiceMapper;
    private final ItemMapper itemMapper;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, UserRepository userRepository, InvoiceMapper invoiceMapper, FileRepository fileRepository, ItemMapper itemMapper) {
        super();
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
        this.invoiceMapper = invoiceMapper;
        this.fileRepository = fileRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public InvoiceDTO saveInvoice(InvoiceDTO invoiceDto) {
        User user = userRepository.findById(invoiceDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found for the id : " + invoiceDto.getUserId()));

        File file = fileRepository.findById(invoiceDto.getFileId()).orElse(null);

        Invoice invoice = invoiceMapper.dtoToInvoice(invoiceDto);
        invoice.setUserInvoice(user);
        invoice.setInvoiceFile(file);

        invoiceRepository.save(invoice);
        return invoiceDto;
    }

    @Override
    public List<InvoiceDTO> getInvoices() {
        return invoiceMapper.invoicesToDtoList(invoiceRepository.findAll());
    }

    @Override
    public InvoiceDTO getInvoiceById(long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found"));
        return invoiceMapper.invoiceToDto(invoice);
    }

    @Override
    public InvoiceDTO updateInvoice(InvoiceDTO newInvoice, long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found"));

        List<Item> items = itemMapper.dtoToItems(newInvoice.getItems());

        invoice.setClientName(newInvoice.getClientName());
        invoice.setItems(items);
        invoice.setTotalAmount(newInvoice.getTotalAmount());
        invoice.setInvoiceNumber(newInvoice.getInvoiceNumber());

        invoiceRepository.save(invoice);

        return invoiceMapper.invoiceToDto(invoice);
    }

    @Override
    public void deleteInvoice(long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found"));

        invoiceRepository.delete(invoice);
    }

}