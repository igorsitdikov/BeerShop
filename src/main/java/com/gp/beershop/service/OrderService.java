package com.gp.beershop.service;

import com.gp.beershop.dto.Customer;
import com.gp.beershop.dto.CustomerOrder;
import com.gp.beershop.dto.OrderRequest;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.exception.NoSuchCustomerException;
import com.gp.beershop.mock.CustomersMock;
import com.gp.beershop.mock.OrderMock;
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

    public Orders addOrder(final OrderRequest request) throws NoSuchCustomerException {
        final Orders orders;
        final Optional<Customer> customer = CustomersMock.getAllValues()
            .stream()
            .filter(c -> c.getId().equals(request.getCustomerId()))
            .findAny();
        if (customer.isEmpty()) {
            throw new NoSuchCustomerException("No customer with id = " + request.getCustomerId() + " was found.");
        }
        final List<CustomerOrder> customerOrderList = request.getGoods()
            .stream()
            .map(el -> CustomerOrder.builder()
                .beer(beerService.getBeerById(el.getId()))
                .count(el.getCount())
                .build())
            .collect(Collectors.toList());
        final Double total = customerOrderList
            .stream()
            .mapToDouble(a -> a.getBeer().getPrice() * a.getCount())
            .sum();
        orders = Orders.builder()
            .id(2)
            .customer(customer.get())
            .processed(false)
            .customerOrders(customerOrderList)
            .total(total)
            .build();

        final Integer lastElementId = OrderMock.size() + 1;
        OrderMock.put(lastElementId, orders);
        return orders;
    }

    public Integer changeOrderStatus(final Integer id, final Orders request) {
        OrderMock.getById(id).setProcessed(true);
        return id;
    }

    public List<Orders> showOrders() {
        return OrderMock.getAllValues();
    }

}
