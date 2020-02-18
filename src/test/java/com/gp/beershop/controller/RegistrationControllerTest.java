package com.gp.beershop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.Customer;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.mock.CustomersMock;
import com.gp.beershop.repository.UserRepository;
import com.gp.beershop.security.UserRole;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;

import java.util.Optional;

import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
@Log
public class RegistrationControllerTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCustomerSignUpIsCreated() throws Exception {
        // given
        // when
        mockMvc.perform(post("/api/user/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                mapper.writeValueAsString(
                                    CustomersMock.getById(1)
                                                         )))
            // then
            .andExpect(status().isCreated())
            .andExpect(content().json(mapper.writeValueAsString(1)));
    }

    @Test
    public void testShowCustomers() throws Exception {
        // given
        // when
        mockMvc.perform(get("/api/user/")
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(
                mapper.writeValueAsString(CustomersMock.getAllValues())));
    }
    @Test
    public void testShowCustomersWithoutToken() throws Exception {
        // given
        // when
        mockMvc.perform(get("/api/user/")
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isForbidden());
    }
}
