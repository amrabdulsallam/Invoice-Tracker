package com.example.Invoicetracker.repository;

import com.example.Invoicetracker.model.Role;
import com.example.Invoicetracker.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(UserRole userRole);

}
