package mock;

import com.gp.beershop.dto.Goods;
import com.gp.beershop.dto.OrderRequest;

import java.util.HashSet;
import java.util.Set;

public class OrderRequestMock {
    private OrderRequestMock() {
    }
    public static OrderRequest getOrderRequestWithEmptyOrder() {
        return OrderRequest.builder()
            .customerId(2L)
            .goods(new HashSet<>())
            .build();
    }
    public static OrderRequest getOrderRequestByIvan() {
        return OrderRequest.builder()
            .customerId(1L)
            .goods(Set.of(
                Goods.builder()
                    .id(2L)
                    .amount(1)
                    .build(),
                Goods.builder()
                    .id(3L)
                    .amount(3)
                    .build()))
            .build();
    }
    public static OrderRequest getOrderRequestByPetr() {
        return OrderRequest.builder()
            .customerId(2L)
            .goods(Set.of(
                Goods.builder()
                    .id(2L)
                    .amount(1)
                    .build(),
                Goods.builder()
                    .id(3L)
                    .amount(3)
                    .build()))
            .build();
    }
    public static OrderRequest getOrderRequestByPetrIntegration() {
        return OrderRequest.builder()
            .customerId(2L)
            .goods(Set.of(
                Goods.builder()
                    .id(3L)
                    .amount(4)
                    .build(),
                Goods.builder()
                    .id(1L)
                    .amount(5)
                    .build()))
            .build();
    }
    public static OrderRequest getOrderRequestByAdmin() {
        return OrderRequest.builder()
            .customerId(3L)
            .goods(Set.of(
                Goods.builder()
                    .id(2L)
                    .amount(1)
                    .build(),
                Goods.builder()
                    .id(3L)
                    .amount(3)
                    .build()))
            .build();
    }
    public static OrderRequest getOrderRequestByAnton() {
        return OrderRequest.builder()
            .customerId(4L)
            .goods(Set.of(
                Goods.builder()
                    .id(2L)
                    .amount(1)
                    .build(),
                Goods.builder()
                    .id(3L)
                    .amount(3)
                    .build()))
            .build();
    }
}
