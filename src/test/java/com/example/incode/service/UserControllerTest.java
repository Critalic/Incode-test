package com.example.incode.service;

import com.example.incode.dto.UserRequestDto;
import com.example.incode.dto.UserResponseDto;
import com.example.incode.exception.NotFoundException;
import com.example.incode.model.User;
import com.example.incode.repository.mongo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    @Resource
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> userCaptor;


    @Test
    void testUserSave() {
        User expectedSaveArgument = new User("name", "name@gmail.com");
        UserRequestDto toSave = new UserRequestDto("name", "name@gmail.com");
        when(userRepository.save(any())).thenReturn(expectedSaveArgument);

        userService.createUser(toSave);
        Mockito.verify(userRepository).save(userCaptor.capture());

        assertEquals(expectedSaveArgument, userCaptor.getValue());
    }

    @Test
    void testUserUpdate() {
        String id = "id";
        User expectedSaveArgument = new User(id, "name", "na@gmail.com");
        UserRequestDto toSave = new UserRequestDto("name", "na@gmail.com");
        when(userRepository.save(any())).thenReturn(expectedSaveArgument);
        when(userRepository.existsById(id)).thenReturn(true);

        userService.updateUser(id, toSave);

        Mockito.verify(userRepository).save(userCaptor.capture());

        assertEquals(expectedSaveArgument, userCaptor.getValue());

    }

    @Test
    void testGetUserById() {
        String id = "id";
        UserResponseDto expectedResponse = new UserResponseDto(id, "name", "name@gmail.com");
        when(userRepository.findById(id)).thenReturn(Optional.of(new User(id, "name", "name@gmail.com")));

        UserResponseDto userResponse =  userService.getUserById(id);

        assertEquals(userResponse, expectedResponse);
    }

    @Test
    void testGetUserByIdEmpty() {
        String id = "id";
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException e = assertThrows(NotFoundException.class, () -> userService.getUserById(id));

        assertEquals(e.getMessage(), String.format("Found no user with id %s", id));
    }

    @Test
    void testUpdateUserEmpty() {
        String id = "id";
        UserRequestDto toSave = new UserRequestDto("name", "name@gmail.com");
        when(userRepository.existsById(id)).thenReturn(false);

        NotFoundException e = assertThrows(NotFoundException.class, () -> userService.updateUser(id, toSave));

        assertEquals(e.getMessage(), String.format("Found no user with id %s", id));
    }

}
