package com.gp.beershop.service;

import com.gp.beershop.dto.*;
import com.gp.beershop.exception.NoSuchCustomerException;
import com.gp.beershop.mapper.OrderMapper;
import com.gp.beershop.mock.CustomersMock;
import com.gp.beershop.mock.OrderMock;
//import com.gp.beershop.repository.OrderRepository;
import com.gp.beershop.repository.OrderRepository;
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
    final OrderRepository orderRepository;
    final BeerService beerService;
    final OrderMapper orderMapper;
//    final OrderRepository orderRepository;
//    final OrderMapper orderMapper;
//    @PostConstruct
//    public void init() {
//        orderRepository.save(
//                orderMapper.sourceToDestination(Orders.builder()
//                        .id(1)
//                        .customer(CustomersMock.getById(1))
//                        .processed(true)
//                        .total(25D)
//                        .customerOrder(List.of(
//                                CustomerOrder.builder()
//                                        .beer(BeerMock.getById(1))
//                                        .count(2)
//                                        .build(),
//                                CustomerOrder.builder().beer(BeerMock.getById(2))
//                                        .count(5)
//                                        .build()
//                        ))
//                        .build()));
//    }

    public Orders addOrder(final OrderRequest request) throws NoSuchCustomerException {
//        log.info("customer Id = " + request.getCustomerId());
//        request.getGoods()
//                .forEach(el -> log.info("goods id = " + el.getId() + " goods value = " + el.getCount()));
        final Orders orders;
        final Optional<Customer> customer = CustomersMock.getAllValues().stream()
                .filter(c -> c.getId().equals(request.getCustomerId())).findAny();
        if (customer.isEmpty()) {
            throw new NoSuchCustomerException("No customer with id = " + request.getCustomerId() + " was found.");
        } else {
            final List<CustomerOrder> customerOrderList = request.getGoods().stream().map(el -> CustomerOrder.builder()
                    .beer(beerService.getBeerById(el.getId()))
                    .count(el.getCount())
                    .build()).collect(Collectors.toList());
            final Double total = customerOrderList.stream().mapToDouble(a -> a.getBeer().getPrice() * a.getCount()).sum();
            orders = Orders.builder()
                    .id(2)
                    .customer(customer.get())
                    .processed(false)
                    .customerOrders(customerOrderList)
                    .total(total)
                    .build();
        }
        final Integer lastElementId = OrderMock.size() + 1;
        OrderMock.put(lastElementId, orders);
        return orders;
    }

    public Integer changeOrderStatus(final Integer id, final Orders request) {
        log.info("processed = " + request.getProcessed());
        OrderMock.getById(id).setProcessed(true);
        log.info("processed in map = " + OrderMock.getById(id).getProcessed());
        return id;
    }

    public List<Orders> showOrders() {
        orderRepository.findAll().stream()
                .map(el -> {
                    log.info(el.getId().toString());
                   return el.convertToOrders() ;
                })
                .forEach(e -> log.info(e.getCustomer().getName()));
        return OrderMock.getAllValues();
    }

}
