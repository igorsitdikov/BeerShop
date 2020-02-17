package com.gp.beershop.controller;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.UserSignInResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends AbstractControllerTest {

    @Test
    public void testCustomerSignIn() throws Exception {
        final String token = signInAsCustomer().replace("Bearer ", "");
        mockMvc.perform(
            post("/api/user/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    mapper.writeValueAsString(
                        AuthRequest.builder()
                            .email("ivan.ivanov@mail.ru")
                            .password("123456")
                            .build())))
            .andExpect(status().isOk())
            .andExpect(content().json(
                mapper.writeValueAsString(
                    UserSignInResponse.builder()
                        .token(token)
                        .build())));
    }
}
