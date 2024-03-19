package com.example.Invoicetracker.service;

import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.service.dto.InvoiceReturnDTO;
import com.example.Invoicetracker.service.dto.UserDTO;
import com.example.Invoicetracker.service.dto.UserLoginDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserDTO saveUser(User user);

    List<UserDTO> getUsers();

    UserDTO getUserById(long userId);

    UserDTO updateUser(User user, long id);

    void deleteUser(long id);

    String checkCredentials(UserLoginDTO user);

    Page<InvoiceReturnDTO> getAllInvoicesByUser(long id, Pageable pageable);

    Page<InvoiceReturnDTO> getAllInvoicesByUserWithSearch(long id, Pageable pageable, String search);

}
