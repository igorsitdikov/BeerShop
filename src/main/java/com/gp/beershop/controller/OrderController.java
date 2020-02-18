package com.gp.beershop.controller;

import com.gp.beershop.dto.OrderRequest;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.exception.NoSuchCustomerException;
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
    public List<Orders> showOrders() {
        return orderService.showOrders();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Orders addOrder(@RequestBody final OrderRequest orderRequest) throws NoSuchCustomerException {
        return orderService.addOrder(orderRequest);
    }

    @PatchMapping(value = "/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Integer changeOrderStatus(@PathVariable final Integer orderId, @RequestBody final Orders orders) {
        return orderService.changeOrderStatus(orderId, orders);
    }
}
