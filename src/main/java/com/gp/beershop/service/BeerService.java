package com.gp.beershop.service;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.IdResponse;
import com.gp.beershop.dto.PriceRequest;
import com.gp.beershop.mock.BeerMock;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Log
@Service
public class BeerService {

    private final IdResponse id = new IdResponse(3);

    public List<Beer> getAllBeer() {
        return BeerMock.getAllValues();
    }

    public Beer getBeerById(Integer id) {
        return BeerMock.getById(id);
    }

    public List<Beer> getBeerFilter(final String beerType) {
        BeerMock.getAllValues().forEach(el -> log.info("type " + el.getType()));
        return BeerMock.getAllValues().stream()
                .filter(b -> b.getType().equals(beerType))
                .collect(Collectors.toList());
    }

    public IdResponse addBeer(final Beer request) {
        log.info("type = " + request.getType());
        log.info("inStock = " + request.getInStock());
        log.info("name = " + request.getName());
        log.info("description = " + request.getDescription());
        log.info("alcohol = " + request.getAlcohol());
        log.info("density = " + request.getDensity());
        log.info("country = " + request.getCountry());
        log.info("price = " + request.getPrice());
        Integer lastElementId = BeerMock.size() + 1;
        BeerMock.put(lastElementId, Beer.builder()
                .id(lastElementId)
                .type(request.getType())
                .inStock(request.getInStock())
                .name(request.getName())
                .description(request.getDescription())
                .alcohol(request.getAlcohol())
                .density(request.getDensity())
                .country(request.getCountry())
                .price(request.getPrice())
                .build());
        return new IdResponse(lastElementId);
    }

    public IdResponse updateBeerById(final Integer beerId, final PriceRequest request) {
        BeerMock.getById(beerId).setPrice(request.getPrice());
        log.info("new price = " + BeerMock.getById(beerId).getPrice());
        return new IdResponse(beerId);
    }

    public IdResponse deleteBeerById(final Integer beerId) {
        BeerMock.delete(beerId);
        return new IdResponse(beerId);
    }
}
