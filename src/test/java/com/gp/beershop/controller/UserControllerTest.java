package com.gp.beershop.controller;

import com.gp.beershop.mock.UsersMock;
import com.gp.beershop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCustomerSignUpIsCreated() throws Exception {
        // given
        userRepository.deleteById(3);
        // when
        mockMvc.perform(post("/api/users/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(
                                UsersMock.getById(3))))
            // then
            .andExpect(status().isCreated());
    }

    @Test
    public void testCustomerSignUpWhenUserAlreadyExists() throws Exception {
        // given
        // when
        mockMvc.perform(post("/api/users/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(
                                UsersMock.getById(1))))
            // then
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testShowCustomers() throws Exception {
        // given
        final String token = signInAsUser(true);
        // when
        mockMvc.perform(get("/api/users")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(List.of(
                UsersMock.convertToUserWithoutPassword(UsersMock.getById(1)),
                UsersMock.convertToUserWithoutPassword(UsersMock.getById(2))))));
    }

    @Test
    public void testShowCustomersWithoutToken() throws Exception {
        // given
        // when
        mockMvc.perform(get("/api/users")
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isForbidden());
    }
}
