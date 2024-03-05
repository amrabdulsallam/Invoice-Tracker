package com.example.Invoicetracker.repository;

import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.model.enums.UserRole;
import com.example.Invoicetracker.repository.bo.UserBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = " " +
            " SELECT DISTINCT u.id,u.email,u.phone , u.signup_date as signupDate, r.id AS roleId, r.role AS roleName " +
            " FROM user u " +
            " LEFT JOIN user_role ur ON u.id = ur.user_id " +
            " LEFT JOIN role r ON ur.role_id = r.id "
    )
    public List<UserBo> getAllUsersWithoutPassword();

    @Query(nativeQuery = true, value = " " +
            " SELECT user.role " +
            " FROM user " +
            " WHERE user.email = email "
    )
    public Optional<UserRole> getUserRoleByEmail(String email);

    public Optional<User> findByEmail(String email);

}
