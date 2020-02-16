package com.gp.beershop.service;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.PriceRequest;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.mapper.BeerMapper;
import com.gp.beershop.repository.BeerRepository;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Log
@Service
public class BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @PostConstruct
    public void init() {
        beerRepository.saveAndFlush(beerMapper.sourceToDestination(Beer.builder()
                .id(1)
                .type("светлое")
                .inStock(true)
                .name("Лидское")
                .description("Лучшее пиво по бабушкиным рецептам")
                .alcohol(5.0)
                .density(11.5)
                .country("Республика Беларусь")
                .price(5D)
                .build()));
        beerRepository.saveAndFlush( beerMapper.sourceToDestination(Beer.builder()
                .id(2)
                .type("темное")
                .inStock(true)
                .name("Аливария")
                .description("Пиво номер 1 в Беларуси")
                .alcohol(4.6)
                .density(10.2)
                .country("Республика Беларусь")
                .price(3D)
                .build()));
        log.info("Beer repo = " + (long) beerRepository.findAll().size());
    }

    public List<Beer> getBeers() {
        final List<Beer> beers = beerRepository.findAll().stream()
                .map(beerMapper::destinationToSource)
                .collect(Collectors.toList());
        return beers;
    }

    public Beer getBeerById(final Integer id) {
        return beerRepository.findById(id).map(beerMapper::destinationToSource).get();
    }

    public List<Beer> getBeerFilter(final String beerType) {
        return beerRepository.findAll().stream()
                .map(beerMapper::destinationToSource)
                .filter(b -> b.getType().equals(beerType))
                .collect(Collectors.toList());
    }

    public Integer addBeer(final Beer request) {
        final BeerEntity beerEntity = beerRepository.saveAndFlush(beerMapper.sourceToDestination(Beer.builder()
                .type(request.getType())
                .inStock(request.getInStock())
                .name(request.getName())
                .description(request.getDescription())
                .alcohol(request.getAlcohol())
                .density(request.getDensity())
                .country(request.getCountry())
                .price(request.getPrice())
                .build()));
        return beerEntity.getId();
    }

    public Integer updateBeerById(final Integer beerId, final PriceRequest request) {
        final Optional<BeerEntity> beerEntity = beerRepository.findById(beerId);
        BeerEntity beer = null;
        if (beerEntity.isPresent()) {
            beer = beerEntity.get();
            beer.setPrice(request.getPrice());
            beerRepository.save(beer);
        }
        log.info("new price = " + beer.getPrice());
        return beer.getId();
    }

    public Integer deleteBeerById(final Integer beerId) {
        beerRepository.deleteById(beerId);
        return beerId;
    }
}
