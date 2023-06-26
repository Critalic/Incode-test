package com.example.incode.service;

import com.example.incode.dto.UserRequestDto;
import com.example.incode.dto.UserResponseDto;
import com.example.incode.exception.AlreadyExistsException;
import com.example.incode.exception.NotFoundException;
import com.example.incode.model.User;
import com.example.incode.repository.mongo.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    Logger logger = LogManager.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserResponseDto> getAllUsers(int page, int pageSize) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(page, pageSize));
        return userPage.map(UserResponseDto::new);
    }

    public UserResponseDto getUserById(String userId) {
            User user = findExistingUser(userId);
            return new UserResponseDto(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public UserResponseDto updateUser(String userId, UserRequestDto userDtoUpdate) {
        if(!userRepository.existsById(userId)) {
            String message = String.format("Found no user with id %s", userId);
            logger.error(message);
            throw new NotFoundException(message);
        }

        User user = userDtoUpdate.mapToUser();
        user.setId(userId);

        return new UserResponseDto(userRepository.save(user));
    }

    public UserResponseDto createUser(UserRequestDto userDto) {
        if(userRepository.existsByEmail(userDto.getEmail())) {
            String message = String.format("User with email %s already exists", userDto.getEmail());
            logger.error(message);
            throw new AlreadyExistsException(message);
        }

        User user = userRepository.save(userDto.mapToUser());
        return new UserResponseDto(user);
    }

    private User findExistingUser(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()) {
            String message = String.format("Found no user with id %s", userId);
            logger.error(message);
            throw new NotFoundException(message);
        }
        return userOptional.get();
    }

}
