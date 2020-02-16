package com.gp.beershop.controller;

import com.gp.beershop.dto.CustomerSignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationControllerTest extends AbstractControllerTest {

    @Test
    public void testCustomerSignUpIsCreated() throws Exception {
        // given
        // when
        mockMvc.perform(post("/api/user/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        mapper.writeValueAsString(
                                CustomerSignUpRequest.builder()
                                        .name("Иван Иванов")
                                        .email("ivan.ivanov@mail.ru")
                                        .password("123456")
                                        .phone("+375331234567")
                                        .build()
                        )))
                // then
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(1)));
    }

}
