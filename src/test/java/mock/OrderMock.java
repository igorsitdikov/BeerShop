package mock;

import com.gp.beershop.dto.CustomerOrder;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.dto.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class OrderMock {
    private OrderMock() {
    }

    private static Map<Long, Orders> ordersMap = new HashMap<>() {{
        put(1L, Orders.builder()
            .id(1L)
            .user(UsersMock.getById(1L))
            .processed(true)
            .total(BigDecimal.valueOf(25).setScale(2))
            .canceled(false)
            .customerOrders(
                List.of(
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(1L))
                        .amount(2)
                        .build(),
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(2L))
                        .amount(5)
                        .build()
                       ))
            .build());
        put(2L, Orders.builder()
            .id(2L)
            .user(UsersMock.getById(2L))
            .processed(false)
            .canceled(false)
            .total(BigDecimal.valueOf(27).setScale(2))
            .customerOrders(
                List.of(
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(2L))
                        .amount(1)
                        .build(),
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(3L))
                        .amount(3)
                        .build()
                       ))
            .build());
        put(3L, Orders.builder()
            .id(3L)
            .user(UsersMock.getById(2L))
            .processed(false)
            .canceled(false)
            .total(BigDecimal.valueOf(57).setScale(2))
            .customerOrders(
                List.of(
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(3L))
                        .amount(4)
                        .build(),
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(1L))
                        .amount(5)
                        .build()
                       ))
            .build());
        put(4L, Orders.builder()
            .id(2L)
            .user(UsersMock.getById(4L))
            .processed(false)
            .canceled(false)
            .total(BigDecimal.valueOf(27).setScale(2))
            .customerOrders(
                List.of(
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(2L))
                        .amount(1)
                        .build(),
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(3L))
                        .amount(3)
                        .build()
                       ))
            .build());
        put(5L, Orders.builder()
            .id(2L)
            .user(UsersMock.getById(4L))
            .processed(false)
            .canceled(true)
            .total(BigDecimal.valueOf(27).setScale(2))
            .customerOrders(
                List.of(
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(2L))
                        .amount(1)
                        .build(),
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(3L))
                        .amount(3)
                        .build()
                       ))
            .build());
    }};

    public static Orders getById(final Long id) {
        return ordersMap.get(id);
    }

    public static List<Orders> getAllValues() {
        return ordersMap.values()
            .stream()
            .map(order -> Orders.builder()
                .id(order.getId())
                .user(
                    User.builder()
                        .id(order.getUser().getId())
                        .firstName(order.getUser().getFirstName())
                        .secondName(order.getUser().getSecondName())
                        .email(order.getUser().getEmail())
                        .phone(order.getUser().getPhone())
                        .build())
                .processed(order.getProcessed())
                .canceled(order.getCanceled())
                .total(order.getTotal())
                .customerOrders(order.getCustomerOrders())
                .build()).collect(Collectors.toList());
    }

    public static List<Orders> getAllValuesBusinessLogic() {
        return ordersMap.entrySet().stream()
            .filter(order -> order.getKey() == 1 || order.getKey() == 3 || order.getKey() == 5)
            .map(Map.Entry::getValue)
            .map(order -> Orders.builder()
                .id(order.getId())
                .user(
                    User.builder()
                        .id(order.getUser().getId())
                        .firstName(order.getUser().getFirstName())
                        .secondName(order.getUser().getSecondName())
                        .email(order.getUser().getEmail())
                        .phone(order.getUser().getPhone())
                        .build())
                .processed(order.getProcessed())
                .canceled(order.getCanceled())
                .total(order.getTotal())
                .customerOrders(order.getCustomerOrders())
                .build()).collect(Collectors.toList());
    }
}
