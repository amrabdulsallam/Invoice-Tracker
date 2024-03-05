package com.example.Invoicetracker.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserWithRoleDTO {

    private long id;

    private String email;

    private String phone;

    private List<RoleDTO> roles;

}
