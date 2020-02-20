package com.gp.beershop.mock;

import com.gp.beershop.dto.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersMock {

    private CustomersMock() {
    }

    private static Map<Integer, Customer> customerMap = new HashMap<>() {{
        put(1, Customer.builder()
            .id(1)
            .name("Иван Иванов")
            .email("ivan.ivanov@mail.ru")
            .phone("+375331234567")
            .build());
        put(2, Customer.builder()
            .id(2)
            .name("Петр Петров")
            .email("petr.petrov@yandex.ru")
            .phone("+375337654321")
            .build());
    }};

    public static Customer getById(final Integer id) {
        return customerMap.get(id);
    }

    public static List<Customer> getAllValues() {
        return new ArrayList<>(customerMap.values());
    }
}
