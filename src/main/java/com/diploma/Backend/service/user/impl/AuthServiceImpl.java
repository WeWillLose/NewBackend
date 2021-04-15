package com.diploma.Backend.service.user.impl;

import com.diploma.Backend.model.User;
import com.diploma.Backend.rest.dto.LoginDTO;
import com.diploma.Backend.rest.dto.SignupDTO;
import com.diploma.Backend.rest.exception.impl.UserNotFoundExceptionImpl;
import com.diploma.Backend.service.user.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements com.diploma.Backend.service.user.AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Override
    public User authenticateUser(@NonNull LoginDTO loginDTO) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            log.info("IN authenticateUser user with username: {}, password : {} wos authenticated",
                loginDTO.getUsername(), loginDTO.getPassword());
            return user;
        }catch (AuthenticationException e){
            log.error("IN authenticateUser auth for user with login: {}, and password: {} failed", loginDTO.getUsername(), loginDTO.getPassword());
            throw  new UserNotFoundExceptionImpl();
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public User registerUser(@NonNull SignupDTO signUpDTO) {
        log.info("IN registerUser signUpDTO: {}",signUpDTO);

        User userToSave = User.builder()
            .username(signUpDTO.getUsername())
            .password(signUpDTO.getPassword())
            .lastName(signUpDTO.getLastName())
            .firstName(signUpDTO.getFirstName())
            .middleName(signUpDTO.getMiddleName())
            .isActive(true)
            .build();
        return userService.createUser(userToSave);
    }
}
