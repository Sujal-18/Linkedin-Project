package com.sujal.LinkedinProject.userService.services;

import com.sujal.LinkedinProject.userService.dto.LoginRequestDTO;
import com.sujal.LinkedinProject.userService.dto.SignupRequestDTO;
import com.sujal.LinkedinProject.userService.dto.UserDTO;
import com.sujal.LinkedinProject.userService.entities.User;
import com.sujal.LinkedinProject.userService.exceptions.BadRequestException;
import com.sujal.LinkedinProject.userService.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.sujal.LinkedinProject.userService.utils.BCrypt.hash;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDTO signUp(SignupRequestDTO signupRequestDTO) {
        log.info("Signup a user with email: {}",signupRequestDTO.getEmail());

        boolean exists = userRepository.existsByEmail(signupRequestDTO.getEmail());
        if(exists){
            throw new BadRequestException("User with this email already exists");
        }

        User user = modelMapper.map(signupRequestDTO, User.class);
        user.setPassword(hash(signupRequestDTO.getPassword())); //ithe error yeu shkte

        user = userRepository.save(user);
        return modelMapper.map(user,UserDTO.class);

    }

    public String login(LoginRequestDTO loginRequestDTO) {

        return "lgon";
    }
}
