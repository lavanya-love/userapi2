package com.example.userapi.Controller;

import com.example.userapi.controller.UserController;
import com.example.userapi.model.Address;
import com.example.userapi.model.UserEntity;
import com.example.userapi.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.json.JSONObject;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureJsonTesters
@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    //to mock the server side
    private MockMvc mvc;

    @MockBean
    //to mock the controller class depedency
    private UserServiceImpl userServiceImpl;

    @Test
    public void getAll_ShouldReturnListOfUsers() throws Exception {

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


       //Act
        this.mvc.perform(get("/user"))
                .andExpect(status().isOk());


        // Assert
        // assertEquals(2, users.size());
    }

    @Test
    public void create_ShouldReturnCreatedUser() throws Exception {
        // Arrange
        UserEntity expectedUser = UserEntity.builder()
                .id(1L)
                .firstname("ghj")
                .lastname("Doe")
                .username("jhgh")
                .email("johndoe@example.com")
                .address(new Address("localhost","county","postcode"))
                .build();
        when(userServiceImpl.createUser(any())).thenReturn(expectedUser);

        // Act
        MvcResult mvcResult = mvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedUser)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        JSONObject responseJson = new JSONObject(responseBody);
        String actualUsername = responseJson.getString("username");
        System.out.println(actualUsername);
        assertEquals(actualUsername, expectedUser.getUsername());

    }


    private static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}