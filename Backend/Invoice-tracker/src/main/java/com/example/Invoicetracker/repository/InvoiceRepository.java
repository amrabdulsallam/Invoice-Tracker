package com.example.Invoicetracker.repository;

import com.example.Invoicetracker.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Modifying
    @Query(nativeQuery = true, value = " " +
            " UPDATE invoice i" +
            " SET i.deleted = true " +
            " WHERE i.id = :invoiceId "
    )
    void softDelete(long invoiceId);

    @Override
    @Query(nativeQuery = true, value = " " +
            " SELECT * " +
            " FROM invoice i" +
            " WHERE " +
            "       i.deleted = false " +
            "   AND " +
            "       i.id = :invoiceId "
    )
    Optional<Invoice> findById(Long invoiceId);

    @Override
    @Query(nativeQuery = true, value = " " +
            " SELECT * " +
            " FROM invoice i" +
            " WHERE i.deleted = false " +
            " ORDER BY i.issue_date DESC "
    )
    List<Invoice> findAll();

}
