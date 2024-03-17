package com.example.Invoicetracker.service.impl;

import com.example.Invoicetracker.exception.DuplicateEmailException;
import com.example.Invoicetracker.exception.UserNotFoundException;
import com.example.Invoicetracker.model.Invoice;
import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.repository.InvoiceRepository;
import com.example.Invoicetracker.repository.UserRepository;
import com.example.Invoicetracker.repository.bo.UserBo;
import com.example.Invoicetracker.security.JwtUtil;
import com.example.Invoicetracker.service.UserService;
import com.example.Invoicetracker.service.dto.InvoiceReturnDTO;
import com.example.Invoicetracker.service.dto.UserDTO;
import com.example.Invoicetracker.service.dto.UserLoginDTO;
import com.example.Invoicetracker.service.mapper.InvoiceMapper;
import com.example.Invoicetracker.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final InvoiceMapper invoiceMapper;
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, JwtUtil jwtUtil, InvoiceMapper invoiceMapper, InvoiceRepository invoiceRepository) {
        super();
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
        this.invoiceMapper = invoiceMapper;
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Saves a new user
     *
     * @param user The user data to be saved in the DB
     * @return UserDTO which contains the saved user info stored in the DB
     * @throws DuplicateEmailException If a user with the same email already exists
     */
    @Override
    public UserDTO saveUser(User user) {
        Optional<User> existsUser = userRepository.findByEmail(user.getEmail());
        if (existsUser.isPresent()) {
            throw new DuplicateEmailException("Duplicated email");
        }
        userRepository.save(user);
        return userMapper.userToDto(user);
    }

    /**
     * Retrieves all users
     *
     * @return List of UserDTO containing details of all users
     */
    @Override
    public List<UserDTO> getUsers() {
        List<UserBo> users = userRepository.getAllUsersWithoutPassword();
        return userMapper.userBoToUserDto(users);
    }

    /**
     * Retrieves a user by its ID
     *
     * @param id The ID of the user to retrieve
     * @return UserDTO which contains the user's data
     */
    @Override
    public UserDTO getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for the id : " + id));
        return userMapper.userToDto(user);
    }

    /**
     * Updates an existing user
     *
     * @param newUser The new user data to be saved in the DB
     * @param id      The ID of the user to update
     * @return UserDTO which represents the updated user
     * @throws UserNotFoundException If the user with the specified ID is not found.
     */
    @Override
    public UserDTO updateUser(User newUser, long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for the id : " + id));

        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());
        user.setRoles(newUser.getRoles());
        user.setPassword(newUser.getPassword());

        userRepository.save(user);

        return userMapper.userToDto(user);
    }

    /**
     * Deletes a user by its ID
     *
     * @param id The ID of the user to delete
     * @throws UserNotFoundException If the user with the specified ID is not found
     */
    @Override
    public void deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for the id : " + id));
        userRepository.delete(user);
    }

    /**
     * Checks user credentials for login
     *
     * @param user User email and password to be checked
     * @return JWT token if credentials are valid
     * @throws UserNotFoundException If the user with the specified email is not found
     */
    @Override
    public String checkCredentials(UserLoginDTO user) {
        User existsUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!existsUser.getPassword().equals(user.getPassword())) {
            return "Wrong password !";
        }

        return jwtUtil.createToken(existsUser);
    }

    /**
     * Retrieves all invoices associated with a user
     *
     * @param id       The ID of the user
     * @param pageable Pageable object for pagination
     * @return Page of InvoiceReturnDTO containing details of invoices associated with the user
     * @throws UserNotFoundException If the user with the specified ID is not found
     */
    @Override
    public Page<InvoiceReturnDTO> getAllInvoicesByUser(long id, Pageable pageable) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Page<Invoice> invoices = invoiceRepository.getAllInvoicesByUser(id, pageable);
        return invoiceMapper.invoicesToDtoListPage(invoices);
    }

    /**
     * Get all invoices associated with a user with a search value
     * @param id    The ID of the user
     * @param pageable Pageable object for pagination
     * @param search Search value
     * @return Page of InvoiceReturnDTO containing details of invoices associated with the user
     * @throws UserNotFoundException If the user with the specified ID is not found
     */
    @Override
    public Page<InvoiceReturnDTO> getAllInvoicesByUserWithSearch(long id, Pageable pageable, String search) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Page<Invoice> invoices = invoiceRepository.getAllInvoicesByUserWithSearch(id, pageable ,search);
        return invoiceMapper.invoicesToDtoListPage(invoices);
    }

}
