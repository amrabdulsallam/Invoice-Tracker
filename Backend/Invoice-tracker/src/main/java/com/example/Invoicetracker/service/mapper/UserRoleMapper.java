package com.example.Invoicetracker.service.mapper;

import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.service.dto.UserWithRoleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    UserWithRoleDTO userRoleToUserRoleDto(User users);

}
