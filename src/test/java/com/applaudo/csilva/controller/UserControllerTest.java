package com.applaudo.csilva.controller;

import com.applaudo.csilva.dto.UserDto;
import com.applaudo.csilva.model.User;
import com.applaudo.csilva.repository.UserRepository;
import com.applaudo.csilva.service.AddressService;
import com.applaudo.csilva.service.PaymentService;
import com.applaudo.csilva.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("When running User controller")
class UserControllerTest {

    UserDto userDTO;
    User user;
    @Autowired
    private MockMvc mvc;


    @MockBean
    private UserService userService;


    @BeforeEach
    void setUp() {
        userDTO = UserDto.builder()
                .email("juanperez@gmail.com")
                .firstName("juan")
                .lastName("perez")
                .build();
        user = User.builder()
                .email("juanperez@gmail.com")
                .firstName("juan")
                .lastName("perez")
                .build();
    }

    @Test
    void getUserById() throws Exception {
        given(userService.getUserById(anyInt())).willReturn(userDTO);
        this.mvc.perform(get("/api/users/{id}", userDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())));
    }
}