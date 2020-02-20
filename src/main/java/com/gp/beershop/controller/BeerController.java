package com.gp.beershop.controller;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.service.BeerService;
import lombok.Data;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
@RequestMapping("/beers")
@BasePathAwareController
public class BeerController {

    private final BeerService beerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public final List<Beer> getBeerFilter(@RequestParam(name = "status", required = false) final String type) {
        if (type != null) {
            return beerService.getBeersByFilter(type);
        }
        return beerService.getBeers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public final Integer addBeer(@RequestBody final Beer request) {
        return beerService.addBeer(request);
    }

    @PutMapping(value = "/{beerId}")
    @ResponseStatus(HttpStatus.OK)
    public final Beer updateBeerById(@RequestBody final Beer beer, @PathVariable final Integer beerId)
        throws NoSuchBeerException {
        return beerService.updateBeerById(beerId, beer);
    }

    @DeleteMapping(value = "/{beerId}")
    @ResponseStatus(HttpStatus.OK)
    public final Integer deleteBeerById(@PathVariable final Integer beerId) throws NoSuchBeerException {
        return beerService.deleteBeerById(beerId);
    }
}
