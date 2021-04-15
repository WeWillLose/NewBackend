package com.diploma.Backend.rest.service.mapper;

import com.diploma.Backend.model.User;
import com.diploma.Backend.rest.dto.UserDTO;

import java.util.List;

public interface IUserMapper {
    List<UserDTO> usersToUserDTOs(List<User> users);

    UserDTO userToUserDTO(User user);

    List<User> userDTOsToUsers(List<UserDTO> userDTOs);

    User userDTOToUser(UserDTO userDTO);

    User userFromId(Long id);
}
