package com.gp.beershop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gp.beershop.dto.Customer;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.mock.CustomersMock;
import com.gp.beershop.repository.UserRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;

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

    @PostConstruct
    public void init() {
        userRepository.save(
            userMapper.sourceToDestination(
                Customer.builder()
                    .id(1)
                    .name("Иван Иванов")
                    .email("ivan.ivanov@mail.ru")
                    .phone("+375331234567")
                    .build()));
        userRepository.save(
            userMapper.sourceToDestination(
                Customer.builder()
                    .id(2)
                    .name("Петр Петров")
                    .email("petr.petrov@yandex.ru")
                    .phone("+375337654321")
                    .build()));
    }

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
    public void testCustomers() throws Exception {
        // given
        // when
        mockMvc.perform(get("/api/user/")
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(
                mapper.writeValueAsString(CustomersMock.getAllValues())));
    }
}
