package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.controller.entity.ResponseEntity;
import com.example.Invoicetracker.model.enums.UserRole;
import com.example.Invoicetracker.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        logger.info("Attempt to assign {} role for user with id {} ", role, userId);
        userRoleService.assignRoleToUser(userId, role);
        logger.info("{} assigned to user with id {} successfully ", role, userId);
        return new ResponseEntity<>("Role is assigned");
    }

    @GetMapping
    public ResponseEntity<?> getRolesAssignedToUser(@PathVariable long userId) {
        logger.info("Attempt to get roles assigned by user with id : " + userId);
        return new ResponseEntity<>(userRoleService.getRolesAssignedToUser(userId));
    }

    @DeleteMapping("/{role}")
    @PreAuthorize("hasRole('SUPER_USER')")
    public ResponseEntity<?> deleteRoleAssignedToUser(@PathVariable long userId, @PathVariable UserRole role) {
        logger.info("Attempt to get roles assigned by user with id : " + userId);
        userRoleService.deleteRoleAssignedToUser(userId, role);
        logger.info("{} assigned to user with id {} is deleted successfully ", role, userId);
        return new ResponseEntity<>();
    }

}
