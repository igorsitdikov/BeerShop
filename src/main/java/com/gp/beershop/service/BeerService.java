package com.gp.beershop.service;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.mapper.BeerMapper;
import com.gp.beershop.repository.BeerRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public List<Beer> getBeers() {
        return beerRepository.findAll()
            .stream()
            .map(beerMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    public Beer getBeerById(final Integer id) throws NoSuchBeerException {
        checkExistingBeer(id);
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
        checkExistingBeer(id);

        beer.setId(id);
        final BeerEntity beerEntity = beerMapper.sourceToDestination(beer);

        beerRepository.save(beerEntity);

        return beerMapper.destinationToSource(beerEntity);
    }

    public void deleteBeerById(final Integer id) throws NoSuchBeerException {
        checkExistingBeer(id);
        beerRepository.deleteById(id);
    }

    private void checkExistingBeer(final Integer id) throws NoSuchBeerException {
        final boolean isFound = beerRepository.existsById(id);
        if (!isFound) {
            throw new NoSuchBeerException("No beer with id = " + id + " was found.");
        }
    }
}
