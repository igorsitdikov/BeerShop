package com.gp.beershop.service;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.repository.UserRepository;
import com.gp.beershop.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Log
@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    public UserSignInResponse signIn(final AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        return new UserSignInResponse(jwtUtil.generateToken(
                new User(request.getEmail(),
                        request.getPassword(),
                        List.of(new SimpleGrantedAuthority("CUSTOMER")))));
//                log.info("email = " + request.getEmail());
//        log.info("email = " + request.getPassword());
//        return id;
    }
}
