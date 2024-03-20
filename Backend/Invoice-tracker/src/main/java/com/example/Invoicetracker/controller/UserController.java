package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.controller.entity.ResponseEntity;
import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.service.UserService;
import com.example.Invoicetracker.service.dto.InvoiceReturnDTO;
import com.example.Invoicetracker.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        logger.info("A new user added with email : " + user.getEmail());
        return new ResponseEntity<>(userService.saveUser(user));
    }

    @GetMapping()
    @PreAuthorize("hasRole('SUPER_USER')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("Getting all users");
        return new ResponseEntity<>(userService.getUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_USER')")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        logger.info("Attempt to get user with id : " + id);
        UserDTO user = userService.getUserById(id);
        logger.info("Successful getting user with id " + id);
        return new ResponseEntity<>(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_USER', 'USER')")
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable long id) {
        logger.info("Attempt to update user with id : " + id);
        UserDTO user = userService.updateUser(newUser, id);
        logger.info("Successful user updated with id " + id);
        return new ResponseEntity<>(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_USER')")
    public ResponseEntity<?> deleteUserById(@PathVariable long id) {
        logger.info("Attempt to delete user with id : " + id);
        userService.deleteUser(id);
        logger.info("Successful user deleted with id " + id);
        return new ResponseEntity<>();
    }

    @GetMapping("/{id}/invoices")
    @PreAuthorize("hasAnyRole('SUPER_USER', 'USER')")
    public ResponseEntity<?> getAllInvoicesByUser(@PathVariable long id,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "3") int size,
                                                  @RequestParam(name = "search", required = false) String search) {
        logger.info("Attempt to get all invoices by user with id : " + id);
        Pageable pageable = PageRequest.of(page, size);
        if (search != null && !search.isEmpty()) {
            Page<InvoiceReturnDTO> invoices = userService.getAllInvoicesByUserWithSearch(id, pageable, search);
            logger.info("Successful getting all invoices by user with {} and search {}", id, search);
            return new ResponseEntity<>(invoices);
        } else {
            Page<InvoiceReturnDTO> invoices = userService.getAllInvoicesByUser(id, pageable);
            logger.info("Successful getting all invoices by user with id " + id);
            return new ResponseEntity<>(invoices);
        }
    }

}
