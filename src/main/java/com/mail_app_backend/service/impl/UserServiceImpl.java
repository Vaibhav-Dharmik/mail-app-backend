package com.mail_app_backend.service.impl;

import com.mail_app_backend.mapper.UserMapper;
import com.mail_app_backend.model.User;
import com.mail_app_backend.model.dto.UserDto;
import com.mail_app_backend.repository.UserRepository;
import com.mail_app_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }
}
