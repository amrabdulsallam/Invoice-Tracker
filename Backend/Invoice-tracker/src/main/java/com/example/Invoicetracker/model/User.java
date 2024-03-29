package com.example.Invoicetracker.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone")
    private String phone;

    @CreationTimestamp
    @Column(name = "signup_date")
    private Date signupDate;

    @OneToMany(mappedBy = "userInvoice")
    private List<Invoice> userInvoices;

    @OneToMany(mappedBy = "userAudit")
    private List<InvoiceAudit> userInvoiceAudit;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles;

    @Override
    public String toString() {
        return "User: " + this.email + " , " + "Role: " + this.roles + " , " + "Signup Date: " + this.signupDate;
    }

}
