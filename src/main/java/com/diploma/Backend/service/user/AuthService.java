package com.diploma.Backend.service.user;

import com.diploma.Backend.model.User;
import com.diploma.Backend.rest.dto.LoginDTO;
import com.diploma.Backend.rest.dto.SignupDTO;
import lombok.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;

public interface AuthService {
    User authenticateUser(@NonNull LoginDTO loginDTO);

    @PreAuthorize("hasRole('ADMIN')")
    User registerUser(@NonNull SignupDTO signUpDTO);
}
