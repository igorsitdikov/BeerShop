package com.gp.beershop.mock;

import com.gp.beershop.dto.Customer;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
public class CustomersMock {
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

    public static void put(Integer id, Customer customer) {
        customerMap.put(id, customer);
    }

    public static Customer getById(Integer id) {
        return customerMap.get(id);
    }

    public static Integer size() {
        return customerMap.size();
    }

    public static Map<Integer, Customer> getAll() {
        return customerMap;
    }

    public static List<Customer> getAllValues() {
        return new ArrayList<>(customerMap.values());
    }
}
