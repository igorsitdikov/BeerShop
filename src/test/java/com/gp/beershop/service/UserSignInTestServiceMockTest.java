package com.gp.beershop.service;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.exception.NoSuchUserException;
import com.gp.beershop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserSignInTestServiceMockTest {
    private static final String EMAIL = "admin@mail.ru";
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthService authService;

    @Test
    public void testSignInNoSuchUserExists() {
        Mockito.doReturn(Optional.empty()).when(userRepository).findByEmail(EMAIL);
        final AuthRequest authRequest = new AuthRequest(EMAIL, "12345");
        assertThrows(NoSuchUserException.class, () -> authService.signIn(authRequest));
    }
}
