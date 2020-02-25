package com.gp.beershop.mock;

import com.gp.beershop.dto.UserDTO;

import java.util.HashMap;
import java.util.Map;

public class UsersMock {
    private UsersMock() {
    }

    private static final Map<Integer, UserDTO> userMap = new HashMap<>() {{
        put(1, UserDTO.builder()
            .id(1)
            .name("Иван Иванов")
            .email("ivan.ivanov@mail.ru")
            .password("123456")
            .phone("+375331234567")
            .build());
        put(2, UserDTO.builder()
            .id(2)
            .name("Петр Петров")
            .email("petr.petrov@yandex.ru")
            .password("654321")
            .phone("+375337654321")
            .build());
        put(3, UserDTO.builder()
            .id(3)
            .name("Алексей Алексеев")
            .email("alex.alexeevov@yandex.ru")
            .password("password")
            .phone("+375337654321")
            .build());
    }};

    public static UserDTO convertToUserWithoutPassword(final UserDTO user) {
        return UserDTO.builder()
            .id(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .phone(user.getPhone())
            .build();
    }

    public static UserDTO getById(final Integer id) {
        return userMap.get(id);
    }
}
