package com.gp.beershop.controller;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.exception.SuchBeerAlreadyExistException;
import com.gp.beershop.service.BeerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/beers")
@Validated
@Tag(name = "Beer Management System")
public class BeerController {

    private final BeerService beerService;

    @GetMapping
    @Operation(summary = "View a list of available beers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public List<Beer> getBeers(
        @Parameter(description = "Beer type from which beers will filter and retrieve")
        @RequestParam(name = "type", required = false) final String type) {
        if (type != null) {
            return beerService.getBeersByFilter(type);
        }
        return beerService.getBeers();
    }

    @PostMapping
    @Operation(summary = "Add a beer")
    @ResponseStatus(HttpStatus.CREATED)
    public Long addBeer(
        @Valid
        @Parameter(description = "Beer object store in database table", required = true)
        @RequestBody final Beer request) throws SuchBeerAlreadyExistException {
        return beerService.addBeer(request);
    }

    @PutMapping(value = "/{beerId}")
    @Operation(summary = "Update a beer")
    @ResponseStatus(HttpStatus.OK)
    public Beer updateBeerById(
        @Valid
        @Parameter(description = "Beer Id to update beer object", required = true)
        @PathVariable final Long beerId,
        @Parameter(description = "Update beer object", required = true)
        @RequestBody final Beer beer)
        throws NoSuchBeerException {
        return beerService.updateBeerById(beerId, beer);
    }


    @DeleteMapping(value = "/{beerId}")
    @Operation(summary = "Delete an employee")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBeerById(
        @Parameter(description = "Beer Id from which beer object will delete from database table", required = true)
        @PathVariable final Long beerId) throws NoSuchBeerException {
        beerService.deleteBeerById(beerId);
    }
}
