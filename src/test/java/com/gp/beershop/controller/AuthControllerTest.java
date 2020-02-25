package com.gp.beershop.controller;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.mock.UsersMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends AbstractControllerTest {
    private final Integer CUSTOMER = 1;

    @Test
    public void testCustomerSignIn() throws Exception {
        final String token = signInAsUser(false);
        mockMvc.perform(
            post("/api/users/sign-in")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    mapper.writeValueAsString(
                        AuthRequest.builder()
                            .email(UsersMock.getById(CUSTOMER).getEmail())
                            .password(UsersMock.getById(CUSTOMER).getPassword())
                            .build())))
            .andExpect(status().isOk());
    }
}
