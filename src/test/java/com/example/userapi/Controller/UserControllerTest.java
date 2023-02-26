package com.example.userapi.Controller;

import com.example.userapi.controller.UserController;
import com.example.userapi.model.UserEntity;
import com.example.userapi.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureJsonTesters
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    //to mock the server side
    private MockMvc mvc;

    @MockBean
    //to mock the controller class depedency
    private UserServiceImpl userServiceImpl;

    @Test
    public void getAll_ShouldReturnListOfProducts() throws Exception {

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
        when(userServiceImpl.getAllUsers()).thenReturn(users);

        this.mvc.perform(get("/user"))
                .andExpect(status().isOk());


        // Verify that the UserServiceImpl method was called
        //verify(userServiceImpl, times(1)).getAllUsers();

    }
    @Test
    public void create_ShouldReturnCreatedUser() throws Exception {
        when(userServiceImpl.createUser(any()))
                .thenReturn(UserEntity.builder()
                        .id(1L)
                        .firstname("John")
                        .lastname("Doe")
                        .username("johndoe")
                        .email("johndoe@example.com")
                        .createdAt(Instant.now())
                        .lastModifiedAt(Instant.now()).build());

        mvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(UserEntity.builder().id(1L).build())))
                .andExpect(status().isOk());
    }

    private static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}