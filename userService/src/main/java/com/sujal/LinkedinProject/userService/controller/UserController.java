package com.sujal.LinkedinProject.userService.controller;

import com.sujal.LinkedinProject.userService.dto.LoginRequestDTO;
import com.sujal.LinkedinProject.userService.dto.SignupRequestDTO;
import com.sujal.LinkedinProject.userService.dto.UserDTO;
import com.sujal.LinkedinProject.userService.services.AuthService;
import com.sujal.LinkedinProject.userService.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class UserController {

    private final AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<UserDTO>  signUp(@RequestBody SignupRequestDTO signupRequestDTO){
        UserDTO userDTO = authService.signUp(signupRequestDTO);
        return new ResponseEntity(userDTO, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO){
        String token = authService.login(loginRequestDTO);
        return ResponseEntity.ok(token);
    }




}
