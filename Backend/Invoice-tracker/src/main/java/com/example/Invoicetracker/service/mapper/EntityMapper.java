package com.example.Invoicetracker.service.mapper;

import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.repository.bo.UserBo;
import com.example.Invoicetracker.service.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class EntityMapper {

    public static UserDTO convertToDto(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setPhone(user.getPhone());
        userDto.setSignupDate(user.getSignupDate());
        return userDto;
    }

    public static List<UserDTO> mapUserBoListToUserDtoList(List<UserBo> userBoList) {
        List<UserDTO> userDtoList = new ArrayList<>();
        for (UserBo userBo : userBoList) {
            UserDTO userDto = new UserDTO();
            userDto.setId(userBo.getId());
            userDto.setSignupDate(userBo.getSignupDate());
            userDto.setEmail(userBo.getEmail());
            userDto.setPhone(userBo.getPhone());
            userDto.setRole(userBo.getRole());

            userDtoList.add(userDto);
        }
        return userDtoList;
    }

}
