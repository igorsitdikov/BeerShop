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
import lombok.extern.java.Log;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
@BasePathAwareController
@RequestMapping(value = "/orders")
@Api(value = "Order Management System")
@Log
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
        @ApiParam(value = "Order ID to change order object", required = true)
        @PathVariable final Integer orderId,
        @ApiParam(value = "Change status of order", required = true)
        @RequestParam(name = "status") final Boolean status)
        throws NoSuchOrderException {
        return orderService.changeOrderStatus(orderId, status);
    }
}
