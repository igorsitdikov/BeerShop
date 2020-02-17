package com.gp.beershop.security;


import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoadUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        } else {
            final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                "ROLE_" + userEntity.get().getUserRole().name());
            return new User(email, userEntity.get().getPassword(), List.of(authority));
        }
    }
}
