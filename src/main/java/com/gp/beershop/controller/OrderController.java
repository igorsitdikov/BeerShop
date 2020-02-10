package com.gp.beershop.controller;

import com.gp.beershop.dto.IdResponse;
import com.gp.beershop.dto.OrderRequest;
import com.gp.beershop.dto.OrderStatus;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.exception.NoSuchCustomerException;
import com.gp.beershop.service.OrderService;
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
@RequestMapping("/admin/")
public class OrderController {

    private final OrderService orderService;

    @GetMapping(value = "/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> showAllOrders() {
        return orderService.showAllOrders();
    }


    @PostMapping(value = "/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public Orders addOrder(@RequestBody final OrderRequest request) throws NoSuchCustomerException {
        return orderService.addOrder(request);
    }

    @PatchMapping(value = "/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public IdResponse updateOrder(@PathVariable final Integer orderId, @RequestBody OrderStatus request) {
        return orderService.updateOrder(orderId, request);
    }
}
