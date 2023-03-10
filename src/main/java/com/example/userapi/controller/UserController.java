package com.example.userapi.controller;


import com.example.userapi.exception.UserAlreadyExistsException;
import com.example.userapi.exception.UserNotFoundException;
import com.example.userapi.model.UserEntity;
import com.example.userapi.service.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userApi")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/users")
    public List<UserEntity> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    @GetMapping("/user")
    public UserEntity checkCredentials(@RequestParam String username, @RequestParam String password) throws UserNotFoundException {
        return userServiceImpl.getUserByUsername(username, password);
    }

    @PostMapping("/user/create")
    public UserEntity create(@RequestBody UserEntity userEntity) throws UserAlreadyExistsException {
        return userServiceImpl.createUser(userEntity);
    }

    @DeleteMapping("/user/{id}")
    public void delete(@PathVariable Long id) {
        userServiceImpl.deleteUserById(id);
    }

    @PutMapping("/user/update/{id}")
    public Optional<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity userEntity) throws UserNotFoundException {
        return userServiceImpl.getUserById(id);
    }
}
