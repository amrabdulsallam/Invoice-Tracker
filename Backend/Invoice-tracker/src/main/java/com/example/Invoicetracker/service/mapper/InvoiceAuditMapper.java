package com.example.Invoicetracker.service.mapper;

import com.example.Invoicetracker.model.InvoiceAudit;
import com.example.Invoicetracker.service.dto.InvoiceAuditDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceAuditMapper {

    @Mapping(source = "userAudit.id", target = "userId")
    @Mapping(source = "invoiceAudit.id", target = "invoiceId")
    List<InvoiceAuditDTO> invoiceAuditsToDtoList(List<InvoiceAudit> invoiceAudits);

    @Mapping(source = "userAudit.id", target = "userId")
    @Mapping(source = "invoiceAudit.id", target = "invoiceId")
    InvoiceAuditDTO invoiceAuditsToDto(InvoiceAudit invoiceAudit);

}
