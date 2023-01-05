package com.billing.service.impl;

import com.billing.constants.CacheConstants;
import com.billing.dto.UserDto;
import com.billing.exception.BadRequestException;
import com.billing.exception.NotFoundException;
import com.billing.model.User;
import com.billing.repository.UserRepository;
import com.billing.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    @Override
    public User findByUserId(Long userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User with User Id: " + userId + " is not found");
        }
        return optionalUser.get();
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User with Email: " + email + " is not found");
        }
        return optionalUser.get();
    }

    @Override
    @Transactional
    public User saveUser(UserDto userDto) {
        log.info("Saving new user {} to the database", userDto.getName());
        Optional<User> optionalUser = userRepo.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()){
            throw new BadRequestException("User with Email: " + userDto.getEmail() + " is found, you cannot save this user");
        }
        User user = User.generateUser(userDto);
        return userRepo.save(user);
    }


    @Override
    @Cacheable(value = CacheConstants.USERS_BY_USERNAME, key = "#email")
    public UserDto getUser(String email) {
        log.info("Fetching user {}", email);
        Optional<User> byUsername = userRepo.findByEmail(email);
        if (byUsername.isEmpty()) {
            throw new NotFoundException("User with Email: " + email + " is not found");
        }
        User user = byUsername.get();
        return UserDto.of(user);
    }

    @Override
    @Cacheable(value = CacheConstants.USERS)
    public List<UserDto> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll().stream().map(UserDto::of).collect(Collectors.toList());
    }
}
