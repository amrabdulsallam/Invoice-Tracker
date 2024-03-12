package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.model.enums.UserRole;
import com.example.Invoicetracker.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users/{userId}/roles")
public class UserRoleController {

    Logger logger = LoggerFactory.getLogger(UserRoleController.class);

    private final UserRoleService userRoleService;

    @Autowired
    public UserRoleController(UserRoleService userRoleService) {
        super();
        this.userRoleService = userRoleService;
    }

    @PostMapping("/{role}")
    @PreAuthorize("hasRole('SUPER_USER')")
    public ResponseEntity<?> assignRoleToUser(@PathVariable long userId, @PathVariable UserRole role) {
        try {
            logger.info("Attempt to assign {} role for user with id {} ", role, userId);
            userRoleService.assignRoleToUser(userId, role);
            logger.info("{} assigned to user with id {} successfully ", role, userId);
            return ResponseEntity.status(HttpStatus.OK).body("Role is assigned");
        } catch (Exception e) {
            logger.error("Failed to assign role to user with id {} : {} ", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getRolesAssignedToUser(@PathVariable long userId) {
        try {
            logger.info("Attempt to get roles assigned by user with id : " + userId);
            return ResponseEntity.status(HttpStatus.OK).body(userRoleService.getRolesAssignedToUser(userId));
        } catch (Exception e) {
            logger.error("Failed to get roles assigned by user with id : " + userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @DeleteMapping("/{role}")
    @PreAuthorize("hasRole('SUPER_USER')")
    public ResponseEntity<?> deleteRoleAssignedToUser(@PathVariable long userId, @PathVariable UserRole role) {
        try {
            logger.info("Attempt to get roles assigned by user with id : " + userId);
            userRoleService.deleteRoleAssignedToUser(userId, role);
            logger.info("{} assigned to user with id {} is deleted successfully ", role, userId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            logger.error("Failed to get roles assigned by user with id : " + userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

}
