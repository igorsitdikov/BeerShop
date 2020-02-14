package com.gp.beershop.controller;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.IdResponse;
import com.gp.beershop.dto.PriceRequest;
import com.gp.beershop.service.BeerService;
import lombok.Data;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/beers")
@BasePathAwareController
public class BeerController {

    private final BeerService beerService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Beer> getBeerFilter(@RequestParam(name = "type", required = false) final String type) {
        if (type != null) {
            return beerService.getBeerFilter(type);
        }
        return beerService.getAllBeer();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public IdResponse addBeer(@RequestBody final Beer request) {
        return beerService.addBeer(request);
    }

    @PatchMapping(value = "/{beerId}")
    @ResponseStatus(HttpStatus.OK)
    public IdResponse updateBeerById(@RequestBody final PriceRequest request, @PathVariable final Integer beerId) {
        return beerService.updateBeerById(beerId, request);
    }

    @DeleteMapping(value = "/{beerId}")
    @ResponseStatus(HttpStatus.OK)
    public IdResponse deleteBeerById(@PathVariable final Integer beerId) {
        return beerService.deleteBeerById(beerId);
    }
}
