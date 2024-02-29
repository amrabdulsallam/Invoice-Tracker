package com.example.Invoicetracker.model;

import com.example.Invoicetracker.model.enums.Role;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @CreationTimestamp
    @Column(name = "signup_date")
    private Date signupDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<File> files;

    @OneToMany(mappedBy = "userHistory", cascade = CascadeType.ALL)
    private List<History> histories;

    @Override
    public String toString() {
        return "User: " + this.email + " , " + "Role: " + this.role + " , " + "Signup Date: " + this.signupDate;
    }

}