package com.gp.beershop.controller;

import com.gp.beershop.dto.OrderRequest;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.exception.NoSuchOrderException;
import com.gp.beershop.exception.NoSuchUserException;
import com.gp.beershop.exception.OrderIsEmptyException;
import com.gp.beershop.exception.SuchUserHasNoPermissionsException;
import com.gp.beershop.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Data;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Data
@RestController
@BasePathAwareController
@RequestMapping(value = "/orders")
@Api(value = "Order Management System")
@Validated
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @ApiOperation(value = "View a list of available orders", response = List.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> showOrders() {
        return orderService.showOrders();
    }


    @PostMapping
    @ApiOperation(value = "Add an order")
    @ResponseStatus(HttpStatus.CREATED)
    public Orders addOrder(
        @Valid
        @RequestHeader("Authorization") final String token,
        @ApiParam(value = "Order object store in database table", required = true)
        @RequestBody final OrderRequest orderRequest)
        throws NoSuchUserException, NoSuchBeerException, OrderIsEmptyException, SuchUserHasNoPermissionsException {
        return orderService.addOrder(orderRequest, token);
    }

    @PatchMapping(value = "/{orderId}")
    @ApiOperation(value = "Change status of order")
    @ResponseStatus(HttpStatus.OK)
    public Integer changeOrderStatus(
            @RequestHeader("Authorization") final String token,
            @ApiParam(value = "Order ID to change order object", required = true)
            @PathVariable final Integer orderId,
            @ApiParam(value = "Change status of order", required = true)
            @RequestParam(name = "status", defaultValue = "false") final Boolean status,
            @ApiParam(value = "Cancel order", required = true)
            @RequestParam(name = "canceled", defaultValue = "false") final Boolean canceled)
            throws NoSuchOrderException, SuchUserHasNoPermissionsException {
        return orderService.changeOrderStatus(orderId, token, status, canceled);
    }

    @DeleteMapping(value = "/{orderId}")
    @ApiOperation(value = "Delete an order")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(
        @ApiParam(value = "Order ID to delete order object", required = true)
        @PathVariable final Integer orderId)
        throws NoSuchOrderException {
        orderService.deleteOrder(orderId);
    }
}
