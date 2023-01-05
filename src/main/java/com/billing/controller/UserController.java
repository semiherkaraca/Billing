package com.billing.controller;

import com.billing.config.ApplicationProperties;
import com.billing.constants.RequestMapConstants;
import com.billing.dto.UserDto;
import com.billing.model.User;
import com.billing.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(RequestMapConstants.USER_PATH)
@RequiredArgsConstructor
public class UserController {

    private final ApplicationProperties applicationProperties;
    private final UserService userService;

    @GetMapping
    @ApiOperation(value = "Get Users")
    public ResponseEntity<List<UserDto>> getUsers() {
        System.out.println(applicationProperties.getInvoiceLimit());
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/byUsername/{username}")
    @ApiOperation(value = "Get User by username")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable(name = "username") String username) {
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @PostMapping("/save")
    @ApiOperation(value = "Save User")
    public ResponseEntity<User> saveUser(@RequestBody UserDto user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
}
