package com.gp.beershop.controller;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.mock.UsersMock;
import com.gp.beershop.repository.UserRepository;
import com.gp.beershop.security.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends AbstractControllerTest {
    private final Integer CUSTOMER = 2;

    @MockBean
    private UserRepository userRepository;
    @SpyBean
    private UserMapper userMapper;

    @Test
    public void testCustomerSignIn() throws Exception {
        // given
        final UserEntity petr = userMapper.sourceToDestination(UsersMock.getById(CUSTOMER));
        petr.setPassword(passwordEncoder.encode(petr.getPassword()));
        petr.setUserRole(UserRole.CUSTOMER);

        willReturn(Optional.of(petr))
            .given(userRepository)
            .findByEmail(petr.getEmail());
        // when
        mockMvc.perform(
            post("/api/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    mapper.writeValueAsString(
                        new AuthRequest(UsersMock.getById(CUSTOMER).getEmail(),
                                        UsersMock.getById(CUSTOMER).getPassword()))))
            // then
            .andExpect(status().isOk());
    }


    @Test
    public void testCustomerSignIn_NoSuchUser() throws Exception {
        // given
        // when
        mockMvc.perform(
            post("/api/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    mapper.writeValueAsString(
                        new AuthRequest(UsersMock.getById(CUSTOMER).getEmail(),
                                        UsersMock.getById(CUSTOMER).getPassword()))))
            // then
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"No user with email = petr.petrov@yandex.ru was found.\"}"));
    }

}
