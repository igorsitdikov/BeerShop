package com.gp.beershop.controller;

import com.gp.beershop.dto.OrderRequest;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.exception.NoSuchUserException;
import com.gp.beershop.exception.NoSuchOrderException;
import com.gp.beershop.service.OrderService;
import lombok.Data;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@BasePathAwareController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public final List<Orders> showOrders() {
        return orderService.showOrders();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public final Orders addOrder(@RequestBody final OrderRequest orderRequest)
        throws NoSuchUserException, NoSuchBeerException {
        return orderService.addOrder(orderRequest);
    }

    @PatchMapping(value = "/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public final Integer changeOrderStatus(@PathVariable final Integer orderId, @RequestParam(name = "status", required = false) final Boolean status)
        throws NoSuchOrderException {
        return orderService.changeOrderStatus(orderId, status);
    }
}
