package com.example.Invoicetracker.service.impl;

import com.example.Invoicetracker.exception.UserNotFoundException;
import com.example.Invoicetracker.model.Role;
import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.model.enums.UserRole;
import com.example.Invoicetracker.repository.RoleRepository;
import com.example.Invoicetracker.repository.UserRepository;
import com.example.Invoicetracker.service.UserRoleService;
import com.example.Invoicetracker.service.dto.UserWithRoleDTO;
import com.example.Invoicetracker.service.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final UserRoleMapper userRoleMapper;

    @Autowired
    public UserRoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository, UserRoleMapper userRoleMapper) {
        super();
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userRoleMapper = userRoleMapper;
    }

    /**
     * Assigns a role to a user
     * @param userId The ID of the user to assign the role
     * @param userRole The role to be assigned such as (USER,SUPER_ADMIN and AUDITOR)
     * @throws UserNotFoundException If the user with the specified ID is not found
     */
    @Override
    public void assignRoleToUser(long userId, UserRole userRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No user found"));
        Role role = roleRepository.findByRole(userRole);
        if (role != null && !user.getRoles().contains(role)) {
            user.getRoles().add(role);
        }
        userRepository.save(user);
    }

    /**
     * Retrieves the roles assigned to a user
     * @param userId The ID of the user
     * @return UserWithRoleDTO containing details of the user and assigned roles
     * @throws UserNotFoundException If the user with the specified ID is not found
     */
    @Override
    public UserWithRoleDTO getRolesAssignedToUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No user found"));

        return userRoleMapper.userRoleToUserRoleDto(user);
    }

    /**
     * Deletes a role assigned to a user
     * @param userId The ID of the user who have the role
     * @param userRole The role to be deleted
     * @throws UserNotFoundException If the user with the specified ID is not found
     */
    @Override
    public void deleteRoleAssignedToUser(long userId, UserRole userRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Role role = roleRepository.findByRole(userRole);
        if (role != null && user.getRoles().contains(role)) {
            user.getRoles().remove(role);
        }
        userRepository.save(user);
    }

}
