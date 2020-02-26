package com.gp.beershop.controller;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.mapper.BeerMapper;
import com.gp.beershop.mapper.OrderMapper;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.mock.UsersMock;
import com.gp.beershop.repository.UserRepository;
import com.gp.beershop.security.UserRole;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("classpath:application-test.properties")
public class AuthControllerTest extends AbstractControllerTest {
    private final Integer CUSTOMER = 2;

    @SpyBean
    private UserMapper userMapper;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void testCustomerSignIn() throws Exception {
        final UserEntity petr = userMapper.sourceToDestination(UsersMock.getById(CUSTOMER));
        petr.setPassword(passwordEncoder.encode(petr.getPassword()));
        petr.setUserRole(UserRole.CUSTOMER);

        willReturn(Optional.of(petr))
            .given(userRepository)
            .findByEmail(petr.getEmail());


        mockMvc.perform(
            post("/api/sign-in")
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
