package com.diploma.Backend.rest.service.mapper.impl;

import com.diploma.Backend.model.User;
import com.diploma.Backend.rest.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserMapperImpl implements com.diploma.Backend.rest.service.mapper.IUserMapper {

    @Override
    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO userToUserDTO(User user) {
        return user == null? null:
         UserDTO.builder()
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .middleName(user.getMiddleName())
            .username(user.getUsername())
            .roles(user.getRoles())
            .id(user.getId())
            .build();
    }


    @Override
    public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
        return userDTOs.stream().filter(Objects::nonNull).map(this::userDTOToUser).collect(Collectors.toList());
    }

    @Override
    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getId());
            user.setUsername(userDTO.getUsername());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setMiddleName(userDTO.getMiddleName());
            user.setRoles(userDTO.getRoles());
            return user;
        }
    }

    @Override
    public User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
