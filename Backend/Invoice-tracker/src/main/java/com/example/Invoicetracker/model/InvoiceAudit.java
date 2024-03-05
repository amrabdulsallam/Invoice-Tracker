package com.example.Invoicetracker.model;

import com.example.Invoicetracker.model.enums.Action;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "invoice_audit")
public class InvoiceAudit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Action action;

    @CreationTimestamp
    @Column(name = "audit_date")
    private Date auditDate;

    @Column(name = "old_value", nullable = false)
    private String oldValue;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userAudit;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoiceAudit;

    @Override
    public String toString() {
        return "Status : " + this.action + " , " + "Date : " + this.auditDate + " , " + "Old Value : " + this.oldValue;
    }

}
