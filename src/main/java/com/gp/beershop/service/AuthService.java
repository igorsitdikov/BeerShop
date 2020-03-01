package com.gp.beershop.service;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.exception.NoSuchUserException;
import com.gp.beershop.repository.UserRepository;
import com.gp.beershop.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Log
@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public UserSignInResponse signIn(final AuthRequest authRequest) throws NoSuchUserException {

        final UserEntity userEntity = userRepository
            .findByEmail(authRequest.getEmail())
            .orElseThrow(
                () -> new NoSuchUserException("No user with email = " + authRequest.getEmail() + " was found."));

        final UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
        authenticationManager.authenticate(authentication);

        final User user = getUserDetails(userEntity);
        return new UserSignInResponse(jwtUtil.generateToken(user));
    }

    private User getUserDetails(final UserEntity userEntity) {
        final String email = userEntity.getEmail();
        final String password = userEntity.getPassword();
        final List<SimpleGrantedAuthority> authorities =
            List.of(new SimpleGrantedAuthority(userEntity.getUserRole().name()));
        return new User(email, password, authorities);
    }
}
