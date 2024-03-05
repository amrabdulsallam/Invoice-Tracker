package com.example.Invoicetracker.service.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDTO {

    private long id;

    private String email;

    private String phone;

    private Date signupDate;

    private List<RoleDTO> roles;

}
