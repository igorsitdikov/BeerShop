package com.gp.beershop.controller;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.BeerRequest;
import com.gp.beershop.dto.IdResponse;
import com.gp.beershop.service.BeerService;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@Log
@RestController
@RequestMapping("/api")
public class BeerController {

    private final BeerService beerService;

    @GetMapping(value = "/beer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Beer> getAllBeer() {
        return beerService.getAllBeer();
    }

    @GetMapping(value = "/beer/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Beer> getBeerFilter(@RequestParam(name = "type") String type) {
        return beerService.getBeerFilter(type);
    }

    @PostMapping(value = "/beer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public IdResponse addBeer(@RequestBody final BeerRequest request) {
        return beerService.addBeer(request);
    }

    @PatchMapping(value = "/beer/{beerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public IdResponse updateBeerById(@PathVariable final Integer beerId) {
        return beerService.updateBeerById(beerId);
    }

    @DeleteMapping(value = "/beer/{beerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public IdResponse deleteBeerById(@PathVariable final Integer beerId) {
        return beerService.deleteBeerById(beerId);
    }
}
