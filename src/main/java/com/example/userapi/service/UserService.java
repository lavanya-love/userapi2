package com.example.userapi.service;

import com.example.userapi.exception.UserAlreadyExistsException;
import com.example.userapi.exception.UserNotFoundException;
import com.example.userapi.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

     List<UserEntity> getAllUsers();

     UserEntity getUserByUsername(String username, String password) throws UserNotFoundException;

     UserEntity createUser(UserEntity userEntity) throws UserAlreadyExistsException;

     Optional<UserEntity> getUserById(Long id);

     void deleteUserById(Long id);

     UserEntity updateUser(Long id,UserEntity userEntity) throws UserNotFoundException;
}
