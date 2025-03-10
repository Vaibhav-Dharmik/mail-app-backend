package com.mail_app_backend.mapper;

import com.mail_app_backend.model.User;
import com.mail_app_backend.model.dto.UserDto;

public class UserMapper {
    public static UserDto mapToUserDto(User user){
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername()
        );
    }
    // Maps UserDto to User entity (excluding password, role, and profile picture)
    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getUsername(),
                null, // password will be handled separately
                "user", // default role
                null  // profile picture
        );
    }
}
