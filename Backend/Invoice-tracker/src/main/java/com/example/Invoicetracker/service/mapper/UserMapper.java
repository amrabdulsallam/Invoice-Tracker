package com.example.Invoicetracker.service.mapper;

import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.repository.bo.UserBo;
import com.example.Invoicetracker.service.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToUser(UserDTO userDTO);

    UserDTO userToDto(User user);

    UserDTO mapUserBoToDto(UserBo userBo);

    List<UserDTO> userBoToUserDto(List<UserBo> userBoList);

}
