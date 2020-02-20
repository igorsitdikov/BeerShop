package com.gp.beershop.security;

import com.gp.beershop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class LoadUserDetailServiceMockTest {

    @Mock
    private UserRepository userRepository;

    @Test
    public void testloadUserByUsernameNotFound() {
        Mockito.doReturn(Optional.empty()).when(userRepository).findByEmail("email@mail.ru");
        assertThrows(UsernameNotFoundException.class, () -> new LoadUserDetailService(userRepository)
            .loadUserByUsername("email@mail.ru"));
    }

}

