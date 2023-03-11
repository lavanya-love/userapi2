package com.example.userapi.repository;
import com.example.userapi.exception.UserAlreadyExistsException;
import com.example.userapi.exception.UserNotFoundException;
import com.example.userapi.model.UserEntity;
import com.example.userapi.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class UserRepoTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @Test
    void test_getAllUsers(){
        List<UserEntity> users = new ArrayList<>();
        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .username("johndoe")
                .email("johndoe@example.com")
                .createdAt(Instant.now())
                .lastModifiedAt(Instant.now())
                .build();
        UserEntity user2 = UserEntity.builder()
                .id(2L)
                .firstname("Jane")
                .lastname("Doe")
                .username("janedoe")
                .email("janedoe@example.com")
                .createdAt(Instant.now())
                .lastModifiedAt(Instant.now())
                .build();
        users.add(user1);
        users.add(user2);
        Mockito.when(userRepo.findAll()).thenReturn(users);
        List<UserEntity> actualUsers = userServiceImpl.getAllUsers();
        assertEquals(users.size(),actualUsers.size());

    }

    @Test
    void test_getUserByUsername_Shouldreturnuser() throws UserNotFoundException {
        UserEntity user = UserEntity.builder()
                .username("janedoe")
                .password("gavin")
                .firstname("jane")
                .build();
        Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
        assertEquals(user.getFirstname(),userServiceImpl.getUserByUsername(user.getUsername(), user.getPassword()).getFirstname());
    }

    @Test
    void test_getUserByUsername_Shouldthrowexception(){
        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .username("johndoe")
                .password("gavin")
                .email("johndoe@example.com")
                .createdAt(Instant.now())
                .lastModifiedAt(Instant.now())
                .build();
        Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserByUsername(user.getUsername(), user.getPassword()));
    }


    @Test
    void test_createUser_throwsException(){
        UserEntity user = UserEntity.builder()
              .id(1L)
              .firstname("John")
              .lastname("Doe")
              .username("johndoe")
              .email("johndoe@example.com")
              .createdAt(Instant.now())
              .lastModifiedAt(Instant.now())
              .build();
        Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
       assertThrows(UserAlreadyExistsException.class, () -> userServiceImpl.createUser(user));
    }

    @Test
    void test_createUser() throws UserAlreadyExistsException {
        UserEntity user = UserEntity.builder()
              .id(1L)
              .firstname("John")
              .lastname("Doe")
              .username("johndoe")
              .email("johndoe@example.com")
              .createdAt(Instant.now())
              .lastModifiedAt(Instant.now())
              .build();
        Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(null);
        Mockito.when(userRepo.save(user)).thenReturn(user);
        assertEquals(userServiceImpl.createUser(user).getId(),user.getId());
    }


}
