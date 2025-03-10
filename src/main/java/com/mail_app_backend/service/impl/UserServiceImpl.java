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

    @Override
    public UserDto getUserProfile(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto updateUserProfile(UserDto userDto) {
        User existingUser = userRepository.findByEmail(userDto.getEmail());

        if (existingUser == null) {
            return null;
        }

        // Update only the necessary fields (excluding email)
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setUsername(userDto.getUsername());

        // Save the updated user
        User updatedUser = userRepository.save(existingUser);

        // Return the updated user as DTO
        return UserMapper.mapToUserDto(updatedUser);
    }


}
