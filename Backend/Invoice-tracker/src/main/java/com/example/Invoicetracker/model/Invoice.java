package com.example.Invoicetracker.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "issue_date")
    @CreationTimestamp
    private Date issueDate;

    @Column(name = "total_amount")
    private Double totalAmount;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File invoiceFile;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "invoice_item",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userInvoice;

    @OneToMany(mappedBy = "invoiceAudit")
    private List<InvoiceAudit> invoiceAuditList;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Override
    public String toString() {
        return "Invoice Number: " + this.invoiceNumber + " , " + "Client Name: " + this.clientName + " , " + "Total Amount: " + this.totalAmount
                + " , " + "Invoice Items: " + this.items;
    }

}
