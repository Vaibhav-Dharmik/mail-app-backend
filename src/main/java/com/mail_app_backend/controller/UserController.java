package com.mail_app_backend.controller;

import com.mail_app_backend.model.dto.LoginRequest;
import com.mail_app_backend.model.dto.UserDto;
import com.mail_app_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
//        User user = userRepository.findByEmail(loginRequest.getEmail());
//        if(user == null || !user.getPassword().equals(loginRequest.getPassword())) {
//            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
//        }
//        return new ResponseEntity<>(UserMapper.mapToUserDto(user), HttpStatus.OK);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        UserDto userDto = userService.loginUser(loginRequest);
        if(userDto == null) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
