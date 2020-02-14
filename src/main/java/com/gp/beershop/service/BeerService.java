package com.gp.beershop.service;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.IdResponse;
import com.gp.beershop.dto.PriceRequest;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.mapper.BeerMapper;
import com.gp.beershop.mock.BeerMock;
import com.gp.beershop.repository.BeerRepository;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Log
@Service
public class BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    private final IdResponse id = new IdResponse(3);

    public List<Beer> getAllBeer() {
        return beerRepository.findAll().stream()
                .map(beerMapper::destinationToSource)
                .collect(Collectors.toList());
    }

    public Beer getBeerById(Integer id) {
        return BeerMock.getById(id);
    }

    public List<Beer> getBeerFilter(final String beerType) {
        return beerRepository.findAll().stream()
                .map(beerMapper::destinationToSource)
                .filter(b -> b.getType().equals(beerType))
                .collect(Collectors.toList());
//        BeerMock.getAllValues().forEach(el -> log.info("type " + el.getType()));
//        return BeerMock.getAllValues().stream()
//                .filter(b -> b.getType().equals(beerType))
//                .collect(Collectors.toList());
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
        BeerEntity ss = beerRepository.saveAndFlush(beerMapper.sourceToDestination(Beer.builder()
                .type(request.getType())
                .inStock(request.getInStock())
                .name(request.getName())
                .description(request.getDescription())
                .alcohol(request.getAlcohol())
                .density(request.getDensity())
                .country(request.getCountry())
                .price(request.getPrice())
                .build()));
        return new IdResponse(ss.getId());
    }

    public IdResponse updateBeerById(final Integer beerId, final PriceRequest request) {
        Optional<BeerEntity> beerEntity = beerRepository.findById(beerId);
        BeerEntity beer = null;
        if (beerEntity.isPresent()) {
            beer = beerEntity.get();
            beer.setPrice(request.getPrice());
            beerRepository.save(beer);
        }
        log.info("new price = " + beer.getPrice());
        return new IdResponse(beer.getId());
    }

    public IdResponse deleteBeerById(final Integer beerId) {
        beerRepository.deleteById(beerId);
        return new IdResponse(beerId);
    }
}
