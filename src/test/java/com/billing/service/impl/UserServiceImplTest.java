package com.billing.service.impl;

import com.billing.dto.UserDto;
import com.billing.exception.BadRequestException;
import com.billing.exception.NotFoundException;
import com.billing.model.User;
import com.billing.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepo;

    @Test
    public void shouldReturnUserWhenUserAvailable() {
        //given
        Optional<User> user = Optional.of(User.builder().id(1L).firstname("Semih").lastname("Erkaraca")
                .email("semiherkaraca@outlook.com").build());

        //when
        when(userRepo.findById(any())).thenReturn(user);
        User byUserId = userService.findByUserId(1L);

        //then
        assertEquals(user.get().getId(), byUserId.getId());
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenUserNotFound() {
        try {
            userService.findByUserId(2L);
        } catch (Exception e) {
            //then
            assertTrue(e instanceof NotFoundException);
            assertEquals("User with User Id: 2 is not found", e.getMessage());
        }
    }

    @Test
    public void shouldReturnUserWhenUserAvailableByEmail() {
        //given
        Optional<User> user = Optional.of(User.builder().id(1L).firstname("Semih").lastname("Erkaraca")
                .email("semiherkaraca@outlook.com").build());

        //when
        when(userRepo.findByEmail(any())).thenReturn(user);
        User byEmail = userService.findByEmail("semiherkaraca@outlook.com");

        //then
        assertEquals(user.get().getEmail(), byEmail.getEmail());
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenUserNotFoundByEmail() {
        String dummyEmail = "dummy@gmail.com";
        try {
            userService.findByEmail(dummyEmail);
        } catch (Exception e) {
            //then
            assertTrue(e instanceof NotFoundException);
            String message = "User with Email: " + dummyEmail + " is not found";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void shouldReturnUserWhenSaveNewUser() {
        //given
        Optional<User> user = Optional.of(User.builder().firstname("Semih").lastname("Erkaraca")
                .email("semiherkaraca@outlook.com").build());
        UserDto userDto = UserDto.builder().email("semiherkaraca@outlook.com")
                .name("Semih").lastname("Erkaraca").build();
        //when
        when(userRepo.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(userRepo.save(any())).thenReturn(user.get());

        User byEmail = userService.saveUser(userDto);

        //then
        assertEquals(user.get().getEmail(), byEmail.getEmail());
    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUserExists() {
        //given
        Optional<User> user = Optional.of(User.builder().firstname("Semih").lastname("Erkaraca")
                .email("semiherkaraca@outlook.com").build());
        UserDto userDto = UserDto.builder().email("semiherkaraca@outlook.com")
                .name("Semih").lastname("Erkaraca").build();
        //when
        when(userRepo.findByEmail(userDto.getEmail())).thenReturn(user);

        try {
            User byEmail = userService.saveUser(userDto);
            //then
            assertEquals(user.get().getEmail(), byEmail.getEmail());
        } catch (Exception e) {
            //then
            assertTrue(e instanceof BadRequestException);
            String message = "User with Email: " + userDto.getEmail() + " is found, you cannot save this user";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void shouldReturnUserWhenGetUserByEmail() {
        //given
        Optional<User> user = Optional.of(User.builder().firstname("Semih").lastname("Erkaraca")
                .email("semiherkaraca@outlook.com").build());
        //when
        when(userRepo.findByEmail("semiherkaraca@outlook.com")).thenReturn(user);

        UserDto userDto = userService.getUser("semiherkaraca@outlook.com");

        //then
        assertEquals(user.get().getEmail(), userDto.getEmail());
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenGetUserByEmail() {
        String dummyEmail = "dummy@gmail.com";
        try {
            userService.getUser(dummyEmail);
        } catch (Exception e) {
            //then
            assertTrue(e instanceof NotFoundException);
            String message = "User with Email: " + dummyEmail + " is not found";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void shouldReturnUsers() {
        //given
        User user1 = User.builder().firstname("Semih").lastname("Erkaraca")
                .email("semiherkaraca@outlook.com").build();
        User user2 = User.builder().firstname("Semih").lastname("Erkaraca")
                .email("semiherkaraca@outlook.com").build();
        List<User> users = Arrays.asList(user1, user2);
        List<UserDto> userDtoList = users.stream().map(UserDto::of).collect(Collectors.toList());
        //when
        when(userRepo.findAll()).thenReturn(users);
        List<UserDto> resultUsers = userService.getUsers();

        //then
        assertEquals(resultUsers.get(0).getEmail(), user1.getEmail());
        assertEquals(resultUsers.get(1).getEmail(), user2.getEmail());
    }
}