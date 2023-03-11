package com.example.userapi.service;


import com.example.userapi.exception.UserAlreadyExistsException;
import com.example.userapi.exception.UserNotFoundException;
import com.example.userapi.model.UserEntity;
import com.example.userapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) throws UserAlreadyExistsException {

        UserEntity existingUser = userRepository.findByUsername(userEntity.getUsername());
        if (existingUser!= null) {
            log.info("User already exists");
            throw new UserAlreadyExistsException("User already exists");
        }
        return userRepository.save(userEntity);
    }
    @Override
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUserById(Long id) {
       userRepository.deleteById(id);
    }

    @Override
    public UserEntity updateUser(Long id,UserEntity userEntity)throws UserNotFoundException {

        return userRepository.findById(id)
                .map(p -> {
                    p.setFirstname(userEntity.getFirstname());
                    p.setLastname(userEntity.getLastname());
                    p.setUsername(userEntity.getUsername());
                    p.setEmail(userEntity.getEmail());
                    p.setPassword(userEntity.getPassword());
                    return userRepository.save(p);
                })
                .orElseThrow(() -> new UserNotFoundException("User Not available"));

    }
}
