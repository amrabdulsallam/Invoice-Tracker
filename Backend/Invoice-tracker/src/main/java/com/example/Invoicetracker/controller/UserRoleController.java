package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.model.enums.UserRole;
import com.example.Invoicetracker.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users/{userId}/roles")
public class UserRoleController {

    private final UserRoleService userRoleService;

    @Autowired
    public UserRoleController(UserRoleService userRoleService) {
        super();
        this.userRoleService = userRoleService;
    }

    @PostMapping("/{role}")
    public ResponseEntity<?> assignRoleToUser(@PathVariable long userId, @PathVariable UserRole role) {
        try {
            userRoleService.assignRoleToUser(userId, role);
            return ResponseEntity.status(HttpStatus.OK).body("Role is assigned");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getRolesAssignedToUser(@PathVariable long userId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userRoleService.getRolesAssignedToUser(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @DeleteMapping("/{role}")
    public ResponseEntity<?> deleteRoleAssignedToUser(@PathVariable long userId, @PathVariable UserRole role) {
        try {
            userRoleService.deleteRoleAssignedToUser(userId, role);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
