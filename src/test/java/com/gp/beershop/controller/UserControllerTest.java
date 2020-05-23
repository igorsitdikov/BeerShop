package com.gp.beershop.controller;

import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.repository.UserRepository;
import com.gp.beershop.security.UserRole;
import mock.UsersMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractControllerTest {

    private final static String ADMIN_EMAIL = "alex.alexeev@gmail.com";
    private final static String CUSTOMER_EMAIL = "ivan.ivanov@mail.ru";

    @MockBean
    private UserRepository userRepository;
    @SpyBean
    private UserMapper userMapper;

    @BeforeEach
    public void initUsers() {
        final UserEntity admin = userMapper.sourceToDestination(UsersMock.getById(ADMIN));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setUserRole(UserRole.ADMIN);

        willReturn(Optional.of(admin)).given(userRepository).findByEmail(ADMIN_EMAIL);

        final UserEntity customer = userMapper.sourceToDestination(UsersMock.getById(1L));
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setUserRole(UserRole.CUSTOMER);

        willReturn(true).given(userRepository).existsById(1L);
        willReturn(Optional.of(customer)).given(userRepository).findById(1L);
        willReturn(Optional.of(customer)).given(userRepository).findByEmail(customer.getEmail());
    }


    @Test
    public void testCustomerSignUpIsCreated() throws Exception {
        // given
        final UserEntity customer = userMapper.sourceToDestination(UsersMock.getById(CUSTOMER));
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setUserRole(UserRole.CUSTOMER);

        willReturn(Optional.empty(), Optional.of(customer)).given(userRepository).findByEmail(customer.getEmail());
        // when
        mockMvc.perform(post("/api/users/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(
                                UsersMock.getById(CUSTOMER))))
            // then
            .andExpect(status().isCreated());
        verify(userRepository, times(3)).findByEmail(customer.getEmail());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testCustomerSignUp_UserAlreadyExists() throws Exception {
        // given
        // when
        mockMvc.perform(post("/api/users/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(
                                UsersMock.getById(1L))))
            // then
            .andExpect(status().isConflict());
        verify(userRepository, times(1)).findByEmail(CUSTOMER_EMAIL);
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }

    @Test
    public void testShowCustomers() throws Exception {
        // given

        final String token = signInAsUser(true);
        final List<UserEntity> userEntityList = List.of(userMapper.sourceToDestination(UsersMock.getById(1L)),
                                                        userMapper.sourceToDestination(UsersMock.getById(2L)));
        userEntityList.forEach(el -> el.setUserRole(UserRole.CUSTOMER));
        willReturn(userEntityList).given(userRepository).findAll();
        // when
        mockMvc.perform(get("/api/users")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(List.of(
                UsersMock.convertToUserWithoutPassword(UsersMock.getById(1L)),
                UsersMock.convertToUserWithoutPassword(UsersMock.getById(2L))))));
        verify(userRepository, times(3)).findByEmail(ADMIN_EMAIL);
        verify(userRepository, times(1)).findAll();
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

    @Test
    public void testDeleteUser() throws Exception {
        // given
        final String token = signInAsUser(true);
        final UserEntity customer = userMapper.sourceToDestination(UsersMock.getById(1L));
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setUserRole(UserRole.CUSTOMER);

        willReturn(true).given(userRepository).existsById(1L);
        willReturn(Optional.of(customer)).given(userRepository).findById(1L);
        // when
        mockMvc.perform(delete("/api/users/1")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk());
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUser_NoSuchUser() throws Exception {
        // given
        final String token = signInAsUser(true);
        willReturn(false).given(userRepository).existsById(1L);
        // when
        mockMvc.perform(delete("/api/users/1")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isNotFound());
        verify(userRepository, times(1)).existsById(1L);
    }
}
