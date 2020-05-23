package com.gp.beershop.service;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.exception.NoSuchUserException;
import com.gp.beershop.exception.SuchUserHasNoPermissionsException;
import com.gp.beershop.repository.UserRepository;
import com.gp.beershop.security.JwtUtil;
import com.gp.beershop.security.UserRole;
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
    private static final Integer BEARER = 7;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public UserSignInResponse signIn(final AuthRequest authRequest) throws NoSuchUserException {

        final UserEntity userEntity = userRepository
            .findByEmail(authRequest.getEmail())
            .orElseThrow(
                () -> new NoSuchUserException(
                    String.format("No user with email = %s was found.", authRequest.getEmail())));

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

    public UserEntity checkPermissionsAndGetUser(final Long id, final String token, final boolean isAdmin)
        throws NoSuchUserException, SuchUserHasNoPermissionsException {
        final String userEmailFromToken = getEmailFromToken(token);

        final UserEntity userEntityFromToken =
            userRepository.findByEmail(userEmailFromToken).get();
        final UserEntity userEntity = getUserById(id);
        if (isAdmin) {
            if (!userEntity.getEmail().equals(userEntityFromToken.getEmail()) &&
                userEntityFromToken.getUserRole() != UserRole.ADMIN) {
                throw new SuchUserHasNoPermissionsException(
                    String.format("Customer with email = %s tried cancel order, but has no permissions.",
                                  userEmailFromToken));
            }
        } else {
            if (!userEntity.getEmail().equals(userEntityFromToken.getEmail())) {
                throw new SuchUserHasNoPermissionsException(
                    String.format("Customer with email = %s tried add order to other account.", userEmailFromToken));
            }
        }

        return userEntityFromToken;
    }

    public UserEntity getUserById(final Long id) throws NoSuchUserException {
        return userRepository.findById(id)
            .orElseThrow(() -> new NoSuchUserException(
                String.format("No customer with id = %s was found.", id)));
    }

    private String getEmailFromToken(final String token) {
        return jwtUtil.extractUsername(token.substring(BEARER));
    }
}
