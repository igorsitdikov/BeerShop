package com.gp.beershop.service;

import com.gp.beershop.dto.Customer;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.exception.SuchUserAlreadyExistException;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.mock.CustomersMock;
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

    @PostConstruct
    public void init() {
        final UserEntity ivan = userMapper.sourceToDestination(CustomersMock.getById(1));
        ivan.setPassword(passwordEncoder.encode("123456"));
        ivan.setUserRole(UserRole.CUSTOMER);
        userRepository.save(ivan);
        userRepository.save(
            userMapper.sourceToDestination(
                CustomersMock.getById(2)));
    }

    public Integer signUp(final Customer customer) throws SuchUserAlreadyExistException {

        if (userRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new SuchUserAlreadyExistException("User with email " + customer.getEmail() + " already exists");
        }
        return saveUser(customer);
    }

    private Integer saveUser(final Customer customer) {
        final UserEntity userEntity = userMapper.sourceToDestination(customer);
        userEntity.setUserRole(UserRole.CUSTOMER);
        userEntity.setPassword(passwordEncoder.encode(customer.getPassword()));
        userRepository.save(userEntity);

        return userEntity.getId();
    }

    public List<Customer> customers() {
        return userRepository.findAll()
            .stream()
            .map(userMapper::destinationToSource)
            .map(user -> Customer.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .build())
            .collect(Collectors.toList());
    }
}
