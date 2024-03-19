package com.example.Invoicetracker.service.mapper;

import com.example.Invoicetracker.model.Invoice;
import com.example.Invoicetracker.service.dto.InvoiceDTO;
import com.example.Invoicetracker.service.dto.InvoiceReturnDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    default Page<InvoiceReturnDTO> invoicesToDtoListPage(Page<Invoice> invoices){
        return invoices.map(this::invoiceToReturnDto);
    };

    @Mapping(source = "userId", target = "userInvoice.id")
    @Mapping(source = "fileId", target = "invoiceFile.id")
    Invoice dtoToInvoice(InvoiceDTO dto);

    @Mapping(source = "userInvoice.id", target = "userId")
    @Mapping(source = "invoiceFile.id", target = "fileId")
    InvoiceDTO invoiceToDto(Invoice invoice);

    @Mapping(source = "userInvoice.id", target = "userId")
    @Mapping(source = "invoiceFile.id", target = "fileId")
    InvoiceReturnDTO invoiceToReturnDto(Invoice invoice);

    @Mapping(source = "userInvoice.id", target = "userId")
    @Mapping(source = "invoiceFile.id", target = "fileId")
    List<InvoiceReturnDTO> invoicesToInvoiceReturnDto(List<Invoice> invoices);

}
