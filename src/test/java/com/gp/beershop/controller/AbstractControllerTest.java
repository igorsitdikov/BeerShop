package com.gp.beershop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.mock.UsersMock;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
@Log
public abstract class AbstractControllerTest {
    private final Integer CUSTOMER = 1;
    private final Integer ADMIN = 3;

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper mapper;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected String signInAsUser(final boolean admin) throws Exception {
        final Integer user = admin ? ADMIN : CUSTOMER;

        final AuthRequest request = AuthRequest.builder()
            .email(UsersMock.getById(user).getEmail())
            .password(UsersMock.getById(user).getPassword())
            .build();

        final String response = mockMvc.perform(
            post("/api/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        return "Bearer " + mapper.readValue(response, UserSignInResponse.class).getToken();
    }
}
