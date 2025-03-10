package com.mail_app_backend.service;

import com.mail_app_backend.model.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto User);
}
