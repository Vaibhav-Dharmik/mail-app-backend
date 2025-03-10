package com.mail_app_backend.service.impl;

import com.mail_app_backend.mapper.UserMapper;
import com.mail_app_backend.model.dto.LoginRequest;
import com.mail_app_backend.model.User;
import com.mail_app_backend.model.dto.UserDto;
import com.mail_app_backend.repository.UserRepository;
import com.mail_app_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

//    @Override
//    public UserDto createUser(UserDto userDto) {
//        User user = UserMapper.mapToUser(userDto);
//        User savedUser = userRepository.save(user);
//        return UserMapper.mapToUserDto(savedUser);
//    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        user.setPassword(userDto.getPassword());  // Ensure password is always set
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return null;
        }
        return UserMapper.mapToUserDto(user);
    }
}
