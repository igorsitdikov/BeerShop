package com.gp.beershop.fish;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.Customer;
import com.gp.beershop.dto.Order;
import com.gp.beershop.dto.Orders;

import java.util.List;

public class FishObject {
    public static final List<Customer> customers = List.of(
            Customer.builder()
                    .id(1)
                    .name("Иван Иванов")
                    .email("ivan.ivanov@mail.ru")
                    .phone("+375331234567")
                    .build(),
            Customer.builder()
                    .id(2)
                    .name("Петр Петров")
                    .email("petr.petrov@yandex.ru")
                    .phone("+375337654321")
                    .build());

    public static final List<Beer> beerList = List.of(
            Beer.builder()
                    .id(1)
                    .type("светлое")
                    .inStock(true)
                    .name("Лидское")
                    .description("Лучшее пиво по бабушкиным рецептам")
                    .alcohol(5.0)
                    .density(11.5)
                    .country("Республика Беларусь")
                    .price(5D)
                    .build(),
            Beer.builder()
                    .id(2)
                    .type("темное")
                    .inStock(true)
                    .name("Аливария")
                    .description("Пиво номер 1 в Беларуси")
                    .alcohol(4.6)
                    .density(10.2)
                    .country("Республика Беларусь")
                    .price(3D)
                    .build(),
            Beer.builder()
                    .id(3)
                    .type("светлое осветлённое")
                    .inStock(true)
                    .name("Pilsner Urquell")
                    .description("непастеризованное")
                    .alcohol(4.2)
                    .density(12.0)
                    .country("Чехия")
                    .price(8D)
                    .build());


    public static final List<Orders> orders = List.of(Orders.builder()
                    .id(1)
                    .customer(customers.get(0))
                    .processed(true)
                    .total(31D)
                    .order(List.of(
                            Order.builder()
                                    .beer(beerList.get(1))
                                    .volume(5)
                                    .build(),
                            Order.builder().beer(beerList.get(2))
                                    .volume(2)
                                    .build()
                    ))
                    .build(),
            Orders.builder().id(2)
                    .customer(customers.get(1))
                    .processed(false)
                    .total(27D)
                    .order(List.of(
                            Order.builder()
                                    .beer(beerList.get(1))
                                    .volume(1)
                                    .build(),
                            Order.builder().beer(beerList.get(2))
                                    .volume(3)
                                    .build()
                    ))
                    .build()
    );
}
