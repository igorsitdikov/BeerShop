package com.gp.beershop.service;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.BeerRequest;
import com.gp.beershop.dto.IdResponse;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Log
@Service
public class BeerService {

    private final List<Beer> beerList = List.of(
            Beer.builder()
                    .id(1)
                    .type("светлое")
                    .in_stock(true)
                    .name("Лидское")
                    .description("Лучшее пиво по бабушкиным рецептам")
                    .alcohol(5D)
                    .density(11.5)
                    .country("Республика Беларусь")
                    .price(5D)
                    .build(),
            Beer.builder()
                    .id(2)
                    .type("темное")
                    .in_stock(false)
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
                    .in_stock(true)
                    .name("Pilsner Urquell")
                    .description("непастеризованное")
                    .alcohol(4.2)
                    .density(12.0)
                    .country("Чехия")
                    .price(8D)
                    .build()
    );

    private final IdResponse id = new IdResponse(3);

    public List<Beer> getAllBeer() {
        return beerList.subList(0, 2);
    }

    public List<Beer> getBeerFilter(final String beerType) {
        return beerList.stream()
                .filter(b -> b.getType().equals(beerType)).collect(Collectors.toList());
    }

    public IdResponse addBeer(final BeerRequest request) {
        log.info("type = " + request.getType());
        log.info("in_stock = " + request.getIn_stock());
        log.info("name = " + request.getName());
        log.info("description = " + request.getDescription());
        log.info("alcohol = " + request.getAlcohol());
        log.info("density = " + request.getDensity());
        log.info("country = " + request.getCountry());
        log.info("price = " + request.getPrice());
        return id;
    }
    public IdResponse updateBeerById(final Integer beerId) {
        return new IdResponse(beerId);
    }
    public IdResponse deleteBeerById(final Integer beerId) {
        return new IdResponse(beerId);
    }
}
