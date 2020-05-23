package com.gp.beershop.service;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.User;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.exception.NoSuchUserException;
import com.gp.beershop.exception.SuchUserAlreadyExistException;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.repository.UserRepository;
import com.gp.beershop.security.UserRole;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Data
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @Transactional
    public UserSignInResponse signUp(final User user)
        throws SuchUserAlreadyExistException, NoSuchUserException {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new SuchUserAlreadyExistException(
                String.format("User with email %s already exists", user.getEmail()));
        }
        saveUser(user);
        return authService.signIn(
            new AuthRequest(user.getEmail(), user.getPassword()));
    }

    private void saveUser(final User user) {
        final UserEntity userEntity = userMapper.sourceToDestination(user);
        userEntity.setUserRole(UserRole.CUSTOMER);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userEntity);
    }

    @Transactional
    public List<User> customers() {
        return userRepository.findAll()
            .stream()
            .filter(el -> el.getUserRole().equals(UserRole.CUSTOMER))
            .map(userMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(final Long userId) throws NoSuchUserException {
        final boolean isFound = userRepository.existsById(userId);
        if (!isFound) {
            throw new NoSuchUserException(String.format("No customer with id = %d was found.", userId));
        }
        userRepository.deleteById(userId);
    }
}
