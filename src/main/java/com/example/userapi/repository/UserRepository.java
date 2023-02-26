package com.example.userapi.repository;

import com.example.userapi.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByUsername(String username);
    //UserEntity findByfirstName(String firstname);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findById(Long id);
}
