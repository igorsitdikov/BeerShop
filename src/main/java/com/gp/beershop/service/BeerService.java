package com.gp.beershop.service;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.BeerRequest;
import com.gp.beershop.dto.IdResponse;
import com.gp.beershop.fish.FishObject;
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
        return FishObject.beerList.subList(0, 2);
    }

    public List<Beer> getBeerFilter(final String beerType) {
        return FishObject.beerList.stream()
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
