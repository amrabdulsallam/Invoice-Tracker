package com.example.Invoicetracker.service.dto;

import com.example.Invoicetracker.model.enums.UserRole;
import lombok.Data;

@Data
public class RoleDTO {

    private long id;

    private UserRole role;

}
