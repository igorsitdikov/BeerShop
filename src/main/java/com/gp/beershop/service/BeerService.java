package com.gp.beershop.service;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.exception.SuchBeerAlreadyExistException;
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

    @Transactional
    public List<Beer> getBeers() {
        return beerRepository.findAll()
            .stream()
            .map(beerMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    @Transactional
    public Beer getBeerById(final Long id) throws NoSuchBeerException {
        checkExistingBeer(id);
        return beerRepository.findById(id)
            .map(beerMapper::destinationToSource)
            .get();
    }

    @Transactional
    public List<Beer> getBeersByFilter(final String beerType) {
        return beerRepository.findAll()
            .stream()
            .map(beerMapper::destinationToSource)
            .filter(b -> b.getType().equals(beerType))
            .collect(Collectors.toList());
    }

    @Transactional
    public Long addBeer(final Beer beer) throws SuchBeerAlreadyExistException {
        if (beerRepository.findFirstByName(beer.getName()).isPresent()) {
            throw new SuchBeerAlreadyExistException(
                String.format("Beer with name = %s already exists.", beer.getName()));
        }

        final BeerEntity beerEntity = beerMapper.sourceToDestination(beer);

        final BeerEntity beerEntityOutput = beerRepository.save(beerEntity);
        return beerEntityOutput.getId();
    }

    @Transactional
    public Beer updateBeerById(final Long id, final Beer beer) throws NoSuchBeerException {
        checkExistingBeer(id);

        beer.setId(id);
        final BeerEntity beerEntity = beerMapper.sourceToDestination(beer);

        beerRepository.save(beerEntity);

        return beerMapper.destinationToSource(beerEntity);
    }

    @Transactional
    public void deleteBeerById(final Long id) throws NoSuchBeerException {
        checkExistingBeer(id);
        beerRepository.deleteById(id);
    }

    private void checkExistingBeer(final Long id) throws NoSuchBeerException {
        final boolean isFound = beerRepository.existsById(id);
        if (!isFound) {
            throw new NoSuchBeerException("No beer with id = " + id + " was found.");
        }
    }
}
