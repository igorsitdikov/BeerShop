package com.gp.beershop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.repository.UserRepository;
import com.gp.beershop.security.UserRole;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
@Log
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper mapper;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @MockBean
    protected UserRepository userRepository;

    protected String signInAsCustomer() throws Exception {
        final UserEntity user = createCustomer();
        willReturn(Optional.of(user)).given(userRepository).findByEmail("ivan.ivanov@mail.ru");

        final String response = mockMvc.perform(
            post("/api/user/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(
                    AuthRequest.builder()
                        .email("ivan.ivanov@mail.ru")
                        .password("123456")
                        .build())))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        return "Bearer " + mapper.readValue(response, UserSignInResponse.class).getToken();
    }

    protected UserEntity createCustomer() {
        final UserEntity customer = new UserEntity();
        customer.setEmail("ivan.ivanov@mail.ru");
        customer.setPassword(passwordEncoder.encode("123456"));
        customer.setUserRole(UserRole.CUSTOMER);
        return customer;
    }

}
