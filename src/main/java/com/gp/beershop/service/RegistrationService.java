package com.gp.beershop.service;

import com.gp.beershop.dto.Customer;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.repository.UserRepository;
import lombok.Data;
import lombok.extern.java.Log;
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

    @PostConstruct
    public void init() {
        userRepository.save(
            userMapper.sourceToDestination(
                Customer.builder()
                    .id(1)
                    .name("Иван Иванов")
                    .email("ivan.ivanov@mail.ru")
                    .phone("+375331234567")
                    .build()));
        userRepository.save(
            userMapper.sourceToDestination(
                Customer.builder()
                    .id(2)
                    .name("Петр Петров")
                    .email("petr.petrov@yandex.ru")
                    .phone("+375337654321")
                    .build()));
    }

    public Integer signUp(final Customer request) {
        return 1;
    }

    public List<Customer> customers() {
        return userRepository.findAll()
            .stream()
            .map(userMapper::destinationToSource)
            .collect(Collectors.toList());
    }
}
