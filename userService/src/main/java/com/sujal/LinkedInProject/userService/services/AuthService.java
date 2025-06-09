package com.sujal.LinkedInProject.userService.services;

import com.sujal.LinkedInProject.userService.dto.LoginRequestDTO;
import com.sujal.LinkedInProject.userService.dto.SignupRequestDTO;
import com.sujal.LinkedInProject.userService.dto.UserDTO;
import com.sujal.LinkedInProject.userService.entities.User;
import com.sujal.LinkedInProject.userService.event.UserCreatedEvent;
import com.sujal.LinkedInProject.userService.exceptions.BadRequestException;
import com.sujal.LinkedInProject.userService.repositories.UserRepository;
import com.sujal.LinkedInProject.userService.utils.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.sujal.LinkedInProject.userService.utils.BCrypt.hash;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    @Value("${kafka.topic.user-created-topic}")
    private String USER_CREATED_TOPIC;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final KafkaTemplate<Long, UserCreatedEvent> userCreatedEventKafkaTemplate;

    public UserDTO signUp(SignupRequestDTO signupRequestDTO) {
        log.info("Signup a user with email: {}",signupRequestDTO.getEmail());

        boolean exists = userRepository.existsByEmail(signupRequestDTO.getEmail());
        if(exists){
            throw new BadRequestException("User with this email already exists");
        }

        User user = modelMapper.map(signupRequestDTO, User.class);
        user.setPassword(hash(signupRequestDTO.getPassword())); //ithe error yeu shkte

        user = userRepository.save(user);

        //Sending userCreated event in kafka
        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder()
                .userId(user.getId())
                .name(user.getName())
                .build();
        userCreatedEventKafkaTemplate.send(USER_CREATED_TOPIC,user.getId(),userCreatedEvent);

        return modelMapper.map(user,UserDTO.class);

    }

    public String login(LoginRequestDTO loginRequestDTO) {
        log.info("Logging in user with email: {}",loginRequestDTO.getEmail());
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(),loginRequestDTO.getPassword())
//        );

        User user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(()-> new BadRequestException("Incorrect email or password"));

        boolean isPasswordMatch = BCrypt.match(loginRequestDTO.getPassword(),user.getPassword());

        if(!isPasswordMatch){
            throw new BadRequestException("Incorrect Email or Password");
        }
        return jwtService.generateAccessToken(user);

    }
}
