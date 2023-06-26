package com.example.incode.controller;

import com.example.incode.dto.UserRequestDto;
import com.example.incode.dto.UserResponseDto;
import com.example.incode.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> read(@RequestParam(required = false, defaultValue = "0", name = "page")
                                                             int page,
                                                             @RequestParam(required = false, defaultValue = "50", name = "pageSize")
                                                             @Max(100) int size) {
        return ResponseEntity.ok(userService.getAllUsers(page, size));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDto> read(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable(name = "id") String id,
                                                          @RequestBody @Valid UserRequestDto updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto newUser) {
        UserResponseDto created = userService.createUser(newUser);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
