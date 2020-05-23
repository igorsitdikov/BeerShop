package com.gp.beershop.controller;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.exception.SuchBeerAlreadyExistException;
import com.gp.beershop.service.BeerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.BasePathAwareController;
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
@BasePathAwareController
@Validated
@Api(value = "Beer Management System")
public class BeerController {

    private final BeerService beerService;

    @GetMapping
    @ApiOperation(value = "View a list of available beers", response = List.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public List<Beer> getBeers(
        @ApiParam(value = "Beer type from which beers will filter and retrieve")
        @RequestParam(name = "type", required = false) final String type) {
        if (type != null) {
            return beerService.getBeersByFilter(type);
        }
        return beerService.getBeers();
    }

    @PostMapping
    @ApiOperation(value = "Add a beer")
    @ResponseStatus(HttpStatus.CREATED)
    public Long addBeer(
        @Valid
        @ApiParam(value = "Beer object store in database table", required = true)
        @RequestBody final Beer request) throws SuchBeerAlreadyExistException {
        return beerService.addBeer(request);
    }

    @PutMapping(value = "/{beerId}")
    @ApiOperation(value = "Update a beer")
    @ResponseStatus(HttpStatus.OK)
    public Beer updateBeerById(
        @Valid
        @ApiParam(value = "Beer Id to update beer object", required = true)
        @PathVariable final Long beerId,
        @ApiParam(value = "Update beer object", required = true)
        @RequestBody final Beer beer)
        throws NoSuchBeerException {
        return beerService.updateBeerById(beerId, beer);
    }


    @DeleteMapping(value = "/{beerId}")
    @ApiOperation(value = "Delete an employee")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBeerById(
        @ApiParam(value = "Beer Id from which beer object will delete from database table", required = true)
        @PathVariable final Long beerId) throws NoSuchBeerException {
        beerService.deleteBeerById(beerId);
    }
}
