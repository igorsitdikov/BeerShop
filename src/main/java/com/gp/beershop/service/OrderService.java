package com.gp.beershop.service;

import com.gp.beershop.dto.*;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.exception.NoSuchCustomerException;
import com.gp.beershop.mock.CustomersMock;
import com.gp.beershop.mock.OrderMock;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Log
@Service
public class OrderService {

    final BeerService beerService;

    public Orders addOrder(OrderRequest request) throws NoSuchCustomerException {
        log.info("customer Id = " + request.getCustomerId());
        request.getGoods()
                .forEach(el -> log.info("goods id = " + el.getId() + " goods value = " + el.getCount()));
        Orders customerOrder;
        Optional<Customer> customer = CustomersMock.getAllValues().stream()
                .filter(c -> c.getId().equals(request.getCustomerId())).findAny();
        if (customer.isEmpty()) {
            throw new NoSuchCustomerException("No customer with id = " + request.getCustomerId() + " was found.");
        } else {
            List<Order> orderList = request.getGoods().stream().map(el -> {
                try {
                    return Order.builder()
                            .beer(beerService.getBeerById(el.getId()))
                            .count(el.getCount())
                            .build();
                } catch (NoSuchBeerException e) {
                    log.info(e.getMessage());
                }
                return null;
            }).collect(Collectors.toList());
            Double total = orderList.stream().mapToDouble(a -> a.getBeer().getPrice() * a.getCount()).sum();
            customerOrder = Orders.builder()
                    .id(2)
                    .customer(customer.get())
                    .processed(false)
                    .order(orderList)
                    .total(total)
                    .build();
        }
        Integer lastElementId = OrderMock.size() + 1;
        OrderMock.put(lastElementId, customerOrder);
        return customerOrder;
    }

    public IdResponse updateOrder(Integer id, Orders request) {
        log.info("processed = " + request.getProcessed());
        OrderMock.getById(id).setProcessed(true);
//        log.info("processed in map = " + OrderMock.getById(id).getProcessed());
        return new IdResponse(id);
    }

    public List<Orders> showAllOrders() {
        return OrderMock.getAllValues();
    }

}
