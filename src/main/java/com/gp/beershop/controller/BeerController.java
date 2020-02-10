package com.gp.beershop.controller;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.BeerRequest;
import com.gp.beershop.dto.IdResponse;
import com.gp.beershop.service.BeerService;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@Log
@RestController
@BasePathAwareController
public class BeerController {

    private final BeerService beerService;

    @GetMapping(value = "/beer")
    @ResponseStatus(HttpStatus.OK)
    public List<Beer> getBeerFilter(@RequestParam(name = "type", required = false) final String type) {
        if (type != null) {
            return beerService.getBeerFilter(type);
        }
        return beerService.getAllBeer();
    }

    @PostMapping(value = "/beer")
    @ResponseStatus(HttpStatus.CREATED)
    public IdResponse addBeer(@RequestBody final BeerRequest request) {
        return beerService.addBeer(request);
    }

    @PatchMapping(value = "/beer/{beerId}")
    @ResponseStatus(HttpStatus.OK)
    public IdResponse updateBeerById(@PathVariable final Integer beerId) {
        return beerService.updateBeerById(beerId);
    }

    @DeleteMapping(value = "/beer/{beerId}")
    @ResponseStatus(HttpStatus.OK)
    public IdResponse deleteBeerById(@PathVariable final Integer beerId) {
        return beerService.deleteBeerById(beerId);
    }
}
