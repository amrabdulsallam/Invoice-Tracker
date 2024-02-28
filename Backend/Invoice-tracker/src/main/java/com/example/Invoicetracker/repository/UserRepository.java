package com.example.Invoicetracker.repository;

import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.model.enums.Role;
import com.example.Invoicetracker.repository.bo.UserBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = " " +
            " SELECT user.id, user.email, user.phone, user.role, user.signup_date " +
            " FROM user "
    )
    public List<UserBo> getAllUsersWithoutPassword();

    @Query(nativeQuery = true, value = " " +
            " SELECT user.role " +
            " FROM user " +
            " WHERE user.email = email "
    )
    public Optional<Role> getUserRoleByEmail(String email);

    public Optional<User> findByEmail(String email);

}
