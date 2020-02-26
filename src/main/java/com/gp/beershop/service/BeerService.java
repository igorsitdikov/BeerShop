package com.gp.beershop.service;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.mapper.BeerMapper;
import com.gp.beershop.mock.BeerMock;
import com.gp.beershop.repository.BeerRepository;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Log
@Service
public class BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @PostConstruct
    @Transactional
    public void init() {
        beerRepository.save(
            beerMapper.sourceToDestination(BeerMock.getById(1)));
        beerRepository.save(
            beerMapper.sourceToDestination(BeerMock.getById(2)));
    }

    public List<Beer> getBeers() {
        return beerRepository.findAll()
            .stream()
            .map(beerMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    public Beer getBeerById(final Integer id) throws NoSuchBeerException {
        checkExisting(id);
        return beerRepository.findById(id)
            .map(beerMapper::destinationToSource)
            .get();
    }

    public List<Beer> getBeersByFilter(final String beerType) {
        return beerRepository.findAll()
            .stream()
            .map(beerMapper::destinationToSource)
            .filter(b -> b.getType().equals(beerType))
            .collect(Collectors.toList());
    }

    @Transactional
    public Integer addBeer(final Beer beer) {
        final BeerEntity beerEntity = beerMapper.sourceToDestination(beer);
        final BeerEntity beerEntityOutput = beerRepository.save(beerEntity);
        return beerEntityOutput.getId();
    }

    public Beer updateBeerById(final Integer id, final Beer beer) throws NoSuchBeerException {
        checkExisting(id);

        beer.setId(id);
        final BeerEntity beerEntity = beerMapper.sourceToDestination(beer);

        beerRepository.save(beerEntity);

        return beerMapper.destinationToSource(beerEntity);
    }

    public void deleteBeerById(final Integer id) throws NoSuchBeerException {
        checkExisting(id);
        beerRepository.deleteById(id);
    }

    private void checkExisting(final Integer id) throws NoSuchBeerException {
        final boolean isFound = beerRepository.existsById(id);
        if (!isFound) {
            throw new NoSuchBeerException("No beer with id = " + id + " was found.");
        }
    }
}
