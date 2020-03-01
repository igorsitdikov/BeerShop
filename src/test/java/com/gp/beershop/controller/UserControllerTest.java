package com.gp.beershop.controller;

import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.mock.UsersMock;
import com.gp.beershop.repository.UserRepository;
import com.gp.beershop.security.UserRole;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractControllerTest {

    @MockBean
    private UserRepository userRepository;
    @SpyBean
    private UserMapper userMapper;

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
        final UserEntity customer = userMapper.sourceToDestination(UsersMock.getById(1));
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setUserRole(UserRole.CUSTOMER);

        willReturn(Optional.of(customer)).given(userRepository).findByEmail(customer.getEmail());

        // when
        mockMvc.perform(post("/api/users/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(
                                UsersMock.getById(1))))
            // then
            .andExpect(status().isConflict());
        verify(userRepository, times(1)).findByEmail(customer.getEmail());
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }

    @Test
    public void testShowCustomers() throws Exception {
        // given
        final UserEntity admin = userMapper.sourceToDestination(UsersMock.getById(ADMIN));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setUserRole(UserRole.ADMIN);

        willReturn(Optional.of(admin)).given(userRepository).findByEmail(admin.getEmail());

        final String token = signInAsUser(true);
        final List<UserEntity> userEntityList = List.of(userMapper.sourceToDestination(UsersMock.getById(1)),
                                                        userMapper.sourceToDestination(UsersMock.getById(2)));
        userEntityList.forEach(el -> el.setUserRole(UserRole.CUSTOMER));
        willReturn(userEntityList).given(userRepository).findAll();
        // when
        mockMvc.perform(get("/api/users")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(List.of(
                UsersMock.convertToUserWithoutPassword(UsersMock.getById(1)),
                UsersMock.convertToUserWithoutPassword(UsersMock.getById(2))))));
        verify(userRepository, times(3)).findByEmail(admin.getEmail());
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
}
