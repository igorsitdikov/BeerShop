package com.gp.beershop.mock;

import com.gp.beershop.dto.Beer;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
public class BeerMock {
    private static Map<Integer, Beer> beerMap = new HashMap<>() {{
        put(1, Beer.builder()
            .id(1)
            .type("светлое")
            .inStock(true)
            .name("Лидское")
            .description("Лучшее пиво по бабушкиным рецептам")
            .alcohol(5.0)
            .density(11.5)
            .country("Республика Беларусь")
            .price(5D)
            .build());
        put(2, Beer.builder()
            .id(2)
            .type("темное")
            .inStock(true)
            .name("Аливария")
            .description("Пиво номер 1 в Беларуси")
            .alcohol(4.6)
            .density(10.2)
            .country("Республика Беларусь")
            .price(3D)
            .build());
        put(3, Beer.builder()
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
    }};

    public static void put(final Integer id, final Beer beer) {
        beerMap.put(id, beer);
    }

    public static Beer getById(final Integer id) {
        return beerMap.get(id);
    }

    public static Integer size() {
        return beerMap.size();
    }

    public static Map<Integer, Beer> getAll() {
        return beerMap;
    }

    public static void defaultState() {
        beerMap = new HashMap<>() {{
            put(1, Beer.builder()
                .id(1)
                .type("светлое")
                .inStock(true)
                .name("Лидское")
                .description("Лучшее пиво по бабушкиным рецептам")
                .alcohol(5.0)
                .density(11.5)
                .country("Республика Беларусь")
                .price(5D)
                .build());
            put(2, Beer.builder()
                .id(2)
                .type("темное")
                .inStock(true)
                .name("Аливария")
                .description("Пиво номер 1 в Беларуси")
                .alcohol(4.6)
                .density(10.2)
                .country("Республика Беларусь")
                .price(3D)
                .build());
            put(3, Beer.builder()
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
        }};
    }

    public static List<Beer> getAllValues() {
        return new ArrayList<>(beerMap.values());
    }

    public static void delete(final Integer id) {
        beerMap.remove(id);
    }
}
