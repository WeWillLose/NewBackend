package com.diploma.Backend.rest.dto;

import com.diploma.Backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * A DTO representing a user, with only the public attributes.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class UserDTO{

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String middleName;

    private Set<Role> roles = new HashSet<>();


}
