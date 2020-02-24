package com.gp.beershop.service;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.UserDTO;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.exception.NoSuchUserException;
import com.gp.beershop.exception.SuchUserAlreadyExistException;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.mock.UsersMock;
import com.gp.beershop.repository.UserRepository;
import com.gp.beershop.security.UserRole;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Data
@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @PostConstruct
    public void init() {
        final UserEntity ivan = userMapper.sourceToDestination(UsersMock.getById(1));
        ivan.setPassword(passwordEncoder.encode("123456"));
        ivan.setUserRole(UserRole.CUSTOMER);
        userRepository.save(ivan);

        final UserEntity petr = userMapper.sourceToDestination(UsersMock.getById(2));
        petr.setPassword(passwordEncoder.encode("123456"));
        petr.setUserRole(UserRole.CUSTOMER);
        userRepository.save(petr);

        final UserEntity admin = userMapper.sourceToDestination(UsersMock.getById(3));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setUserRole(UserRole.ADMIN);
        userRepository.save(admin);
    }

    public UserSignInResponse signUp(final UserDTO userDTO)
        throws SuchUserAlreadyExistException, NoSuchUserException {

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new SuchUserAlreadyExistException("User with email " + userDTO.getEmail() + " already exists");
        }
        saveUser(userDTO);
        return authService.signIn(
            new AuthRequest(userDTO.getEmail(), userDTO.getPassword()));
    }

    private void saveUser(final UserDTO userDTO) {
        final UserEntity userEntity = userMapper.sourceToDestination(userDTO);
        userEntity.setUserRole(UserRole.CUSTOMER);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userEntity);
    }

    public List<UserDTO> customers() {
        return userRepository.findAll()
            .stream()
            .filter(el -> el.getUserRole().equals(UserRole.CUSTOMER))
            .map(userMapper::destinationToSource)
            .collect(Collectors.toList());
    }
}
