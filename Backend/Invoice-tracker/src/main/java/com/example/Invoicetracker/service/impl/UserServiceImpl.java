package com.example.Invoicetracker.service.impl;

import com.example.Invoicetracker.exception.UserNotFoundException;
import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.repository.UserRepository;
import com.example.Invoicetracker.repository.bo.UserBo;
import com.example.Invoicetracker.service.UserService;
import com.example.Invoicetracker.service.dto.UserDTO;
import com.example.Invoicetracker.service.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO saveUser(User user) {
        User newUser = userRepository.save(user);
        return EntityMapper.convertToDto(newUser);
    }

    @Override
    public List<UserDTO> getUsers() {
        List<UserBo> users = userRepository.getAllUsersWithoutPassword();
        return EntityMapper.mapUserBoListToUserDtoList(users);
    }

    @Override
    public UserDTO getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for the id : " + id));
        return EntityMapper.convertToDto(user);
    }

    @Override
    public UserDTO updateUser(User newUser, long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for the id : " + id));

        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());
        user.setRole(newUser.getRole());
        user.setPassword(newUser.getPassword());

        userRepository.save(user);

        return EntityMapper.convertToDto(user);
    }

    @Override
    public void deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for the id : " + id));
        userRepository.delete(user);
    }

}
