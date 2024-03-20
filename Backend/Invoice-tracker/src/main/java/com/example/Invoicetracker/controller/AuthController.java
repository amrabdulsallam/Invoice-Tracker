package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.controller.entity.ResponseEntity;
import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.service.UserService;
import com.example.Invoicetracker.service.dto.UserLoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> checkUserCredentials(@RequestBody UserLoginDTO user) {
        logger.info("Attempting to log in user : " + user.getEmail());
        String token = userService.checkCredentials(user);
        logger.info("User have successfully logged in : " + user.getEmail());
        return new ResponseEntity<>(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        logger.info("Attempting to sign up with info : " + user);
        return new ResponseEntity<>(userService.saveUser(user));
    }

}
