package com.gp.beershop.controller;

import com.gp.beershop.dto.OrderRequest;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.exception.NoSuchOrderException;
import com.gp.beershop.exception.NoSuchUserException;
import com.gp.beershop.exception.OrderIsEmptyException;
import com.gp.beershop.exception.SuchUserHasNoPermissionsException;
import com.gp.beershop.service.OrderService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/orders")
@Tag(name = "Order Management System")
@Validated
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(description = "View a list of available orders")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> showOrders() {
        return orderService.showOrders();
    }


    @PostMapping
    @Operation(description = "Add an order")
    @ResponseStatus(HttpStatus.CREATED)
    public Orders addOrder(
        @Valid
        @RequestHeader("Authorization") final String token,
        @Parameter(description = "Order object store in database table", required = true)
        @RequestBody final OrderRequest orderRequest)
        throws NoSuchUserException, NoSuchBeerException, OrderIsEmptyException, SuchUserHasNoPermissionsException {
        return orderService.addOrder(orderRequest, token);
    }

    @PatchMapping(value = "/{orderId}")
    @Operation(description = "Change status of order")
    @ResponseStatus(HttpStatus.OK)
    public Long changeOrderStatus(
        @RequestHeader("Authorization") final String token,
        @Parameter(description = "Order ID to change order object", required = true)
        @PathVariable final Long orderId,
        @Parameter(description = "Change status of order", required = true)
        @RequestParam(name = "status", defaultValue = "false") final Boolean status,
        @Parameter(description = "Cancel order", required = true)
        @RequestParam(name = "canceled", defaultValue = "false") final Boolean canceled)
        throws NoSuchOrderException, SuchUserHasNoPermissionsException, NoSuchUserException {
        return orderService.changeOrderStatus(orderId, token, status, canceled);
    }

    @DeleteMapping(value = "/{orderId}")
    @Operation(description = "Delete an order")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(
        @Parameter(description = "Order ID to delete order object", required = true)
        @PathVariable final Long orderId)
        throws NoSuchOrderException {
        orderService.deleteOrder(orderId);
    }
}
