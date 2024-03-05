package com.example.Invoicetracker.service;

import com.example.Invoicetracker.model.enums.UserRole;
import com.example.Invoicetracker.service.dto.UserWithRoleDTO;

public interface UserRoleService {

    void assignRoleToUser(long userId, UserRole role);

    UserWithRoleDTO getRolesAssignedToUser(long userId);

    void deleteRoleAssignedToUser(long userId, UserRole userRole);

}
