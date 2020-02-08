package com.gp.beershop.controller;

import com.gp.beershop.dto.IdResponse;
import com.gp.beershop.dto.OrderRequest;
import com.gp.beershop.dto.OrderStatus;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.service.OrdersService;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@Log
@RestController
@RequestMapping("/api/admin/")
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> showAllOrders() {
        return ordersService.showAllOrders();
    }


    @PostMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Orders addOrder(@RequestBody final OrderRequest request) {
        return ordersService.addOrder(request);
    }

    @PatchMapping(value = "/orders/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public IdResponse updateOrder(@PathVariable final Integer orderId, @RequestBody OrderStatus request) {
        return ordersService.updateOrder(orderId, request);
    }
}
