package com.diploma.Backend.rest.controller;

import com.diploma.Backend.rest.dto.LoginDTO;
import com.diploma.Backend.rest.dto.SignupDTO;
import com.diploma.Backend.rest.dto.UserDTO;
import com.diploma.Backend.rest.service.mapper.IUserMapper;
import com.diploma.Backend.service.user.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final IUserMapper userMapper;


    @PostMapping("login")
    public ResponseEntity<UserDTO> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {
        UserDTO userDTO = userMapper.userToUserDTO(authService.authenticateUser(loginDTO));
        return ResponseEntity.ok().body(userDTO);

    }

    @PostMapping("registration")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDTO signUpDTO) {
        return ResponseEntity.ok().body(userMapper.userToUserDTO(authService.registerUser(signUpDTO)));
    }

}
