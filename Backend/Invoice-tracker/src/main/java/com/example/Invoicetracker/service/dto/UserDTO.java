package com.example.Invoicetracker.service.dto;

import com.example.Invoicetracker.model.enums.Role;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {

    private long id;

    private String email;

    private String phone;

    private Role role;

    private Date signupDate;

}
