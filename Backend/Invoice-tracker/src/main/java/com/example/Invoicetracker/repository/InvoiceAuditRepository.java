package com.example.Invoicetracker.repository;

import com.example.Invoicetracker.model.InvoiceAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InvoiceAuditRepository extends JpaRepository<InvoiceAudit, Long> {

    @Query(nativeQuery = true, value = " " +
            " SELECT * " +
            " FROM history " +
            " WHERE history.user_id = userId "
    )
    public Optional<List<InvoiceAudit>> getUserAudits(long userId);

    @Query(nativeQuery = true, value = " " +
            " SELECT * " +
            " FROM history " +
            " WHERE history.invoice_id = invoiceId "
    )
    public Optional<List<InvoiceAudit>> getInvoiceAudits(long invoiceId);

}
