package com.gp.beershop.controller;

import com.gp.beershop.mock.CustomersMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("classpath:application-test.properties")
public class RegistrationControllerTest extends AbstractControllerTest {


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
    public void testShowCustomers() throws Exception {
        // given
        final String token = signInAsCustomer();
        // when
        mockMvc.perform(get("/api/user")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(
                CustomersMock.getAllValues())));
    }

    @Test
    public void testShowCustomersWithoutToken() throws Exception {
        // given
        // when
        mockMvc.perform(get("/api/user")
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isForbidden());
    }
}
