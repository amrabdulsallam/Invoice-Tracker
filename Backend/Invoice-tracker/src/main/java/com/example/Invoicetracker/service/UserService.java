package com.example.Invoicetracker.service;

import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.service.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO saveUser(User user);

    List<UserDTO> getUsers();

    UserDTO getUserById(long userId);

    UserDTO updateUser(User user, long id);

    void deleteUser(long id);

}
