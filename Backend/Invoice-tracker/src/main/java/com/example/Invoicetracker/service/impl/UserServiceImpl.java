package com.example.Invoicetracker.service.impl;

import com.example.Invoicetracker.exception.DuplicateEmailException;
import com.example.Invoicetracker.exception.UserNotFoundException;
import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.repository.UserRepository;
import com.example.Invoicetracker.repository.bo.UserBo;
import com.example.Invoicetracker.security.JwtUtil;
import com.example.Invoicetracker.service.UserService;
import com.example.Invoicetracker.service.dto.UserDTO;
import com.example.Invoicetracker.service.dto.UserLoginDTO;
import com.example.Invoicetracker.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, JwtUtil jwtUtil) {
        super();
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDTO saveUser(User user) {
        Optional<User> existsUser = userRepository.findByEmail(user.getEmail());
        if (existsUser.isPresent()) {
            throw new DuplicateEmailException("Duplicated email");
        }
        userRepository.save(user);
        return userMapper.userToDto(user);
    }

    @Override
    public List<UserDTO> getUsers() {
        List<UserBo> users = userRepository.getAllUsersWithoutPassword();
        return userMapper.userBoToUserDto(users);
    }

    @Override
    public UserDTO getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for the id : " + id));
        return userMapper.userToDto(user);
    }

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

    @Override
    public void deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for the id : " + id));
        userRepository.delete(user);
    }

    @Override
    public String checkCredentials(UserLoginDTO user) {
        User existsUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!existsUser.getPassword().equals(user.getPassword())) {
            return "Wrong password !";
        }

        return jwtUtil.createToken(existsUser);
    }

}
