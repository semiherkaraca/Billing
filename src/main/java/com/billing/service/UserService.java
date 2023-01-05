package com.billing.service;

import com.billing.dto.UserDto;
import com.billing.model.User;

import java.util.List;

public interface UserService {

    User findByUserId(Long userId);

    User findByEmail(String email);

    User saveUser(UserDto userDto);

    UserDto getUser(String email);

    List<UserDto> getUsers();


}
