package com.example.Invoicetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "invoice")
    @JsonIgnore
    private List<InvoiceItem> invoiceItems;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userInvoice;

    @OneToMany(mappedBy = "invoiceAudit")
    @JsonIgnore
    private List<InvoiceAudit> invoiceAuditList;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(InvoiceItem item : invoiceItems){
            stringBuilder.append(item.getItem().getName())
                    .append("Quantity: ").append(item.getQuantity())
                    .append(" Price :").append(item.getItem().getPrice());
        }
        return "Invoice Number: " + this.invoiceNumber + " , " + "Client Name: " + this.clientName + " , " + "Total Amount: " + this.totalAmount
                + " , " + "Invoice Items: " + stringBuilder;
    }

}
