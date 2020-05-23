package com.gp.beershop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.CustomerOrder;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.dto.UserSignInResponse;
import mock.UsersMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {
    protected final static Long ORDER_ID = 2L;
    protected final static Long CUSTOMER = 2L;
    protected final static Long ADMIN = 3L;
    protected final static Long LIDSKOE = 1L;
    protected final static Long ALIVARIA = 2L;
    protected final static Long PILSNER = 3L;
    protected final static Long KRYNICA = 4L;

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper mapper;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected String signInAsUser(final boolean admin) throws Exception {
        // given
        final Long user = admin ? ADMIN : CUSTOMER;

        final AuthRequest request = AuthRequest.builder()
            .email(UsersMock.getById(user).getEmail())
            .password(UsersMock.getById(user).getPassword())
            .build();
        // when
        final String response = mockMvc.perform(
            post("/api/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            // then
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        return "Bearer " + mapper.readValue(response, UserSignInResponse.class).getToken();
    }

    protected List<CustomerOrder> sortCustomerOrders(final Orders orders) {
        return orders.getCustomerOrders().stream().sorted(
            Comparator.comparingLong(o -> o.getBeer().getId()))
            .collect(Collectors.toList());
    }
}
