package com.gp.beershop.service;

import com.gp.beershop.dto.*;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.exception.NoSuchCustomerException;
import com.gp.beershop.fish.FishObject;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Log
@Service
public class OrdersService {

    public Orders addOrder(OrderRequest request) throws NoSuchCustomerException {
        log.info("customer Id = " + request.getCustomerId());
        request.getGoods()
                .forEach(el -> log.info("goods id = " + el.getId() + " goods value = " + el.getValue()));
        Orders customerOrder;
        Optional<Customer> customer = FishObject.customers.stream()
                .filter(c -> c.getId().equals(request.getCustomerId())).findAny();
        if (customer.isEmpty()) {
            throw new NoSuchCustomerException("No customer with id = " + request.getCustomerId() + " was found.");
        } else {

            List<Order> orderList = request.getGoods().stream().map(el -> {
                try {
                    return Order.builder()
                            .beer(FishObject.beerList.stream()
                                    .filter(b -> b.getId().equals(el.getId()))
                                    .findAny()
                                    .orElseThrow(() -> new NoSuchBeerException("No beer with id = " + el.getId() + " was found.")))
                            .volume(el.getValue())
                            .build();
                } catch (NoSuchBeerException e) {
                    log.info(e.getMessage());
                }
                return null;
            }).collect(Collectors.toList());
            Double total = orderList.stream().mapToDouble(a -> a.getBeer().getPrice() * a.getVolume()).sum();
            customerOrder = Orders.builder()
                    .id(2)
                    .customer(customer.get())
                    .processed(false)
                    .order(orderList)
                    .total(total)
                    .build();
        }
        return customerOrder;
    }

    public IdResponse updateOrder(Integer id, OrderStatus request) {
        log.info("processed = " + request.getProcessed());
        return new IdResponse(id);
    }

    public List<Orders> showAllOrders() {
        return FishObject.orders;
    }

}
