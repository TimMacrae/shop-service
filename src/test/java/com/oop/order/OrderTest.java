package com.oop.order;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    OrderItem item1 = new OrderItem(UUID.randomUUID(), "Apple", BigDecimal.valueOf(2.5), 3);
    OrderItem item2 = new OrderItem(UUID.randomUUID(), "Banana", BigDecimal.valueOf(1.0), 5);

    @Test
    void constructor_shouldCalculateTotalSumCorrectly() {
        UUID id = UUID.randomUUID();
        Map<UUID, OrderItem> items = new HashMap<>();

        items.put(item1.productId(), item1);
        items.put(item2.productId(), item2);
        Order order = new Order(id, items);

        BigDecimal expected = item1.price().multiply(BigDecimal.valueOf(item1.quantity()))
                .add(item2.price().multiply(BigDecimal.valueOf(item2.quantity())));
        assertEquals(expected, order.totalSum());
        assertEquals(OrderStatus.PROCESSING, order.orderStatus());
        assertNotEquals(OrderStatus.IN_DELIVERY, order.orderStatus());
    }

    @Test
    void withItems_shouldReturnNewOrderWithUpdatedItemsAndTotalSum() {
        UUID id = UUID.randomUUID();
        Map<UUID, OrderItem> items = new HashMap<>();
        items.put(item1.productId(), item1);
        Order order = new Order(id, items);

        Map<UUID, OrderItem> newItems = new HashMap<>(items);
        newItems.put(item2.productId(), item2);

        Order updatedOrder = order.withItems(newItems);
        BigDecimal expected = item1.price().multiply(BigDecimal.valueOf(item1.quantity()))
                .add(item2.price().multiply(BigDecimal.valueOf(item2.quantity())));

        assertEquals(expected, updatedOrder.totalSum());
        assertNotEquals(order, updatedOrder);
        assertEquals(OrderStatus.PROCESSING, updatedOrder.orderStatus());
        assertNotEquals(OrderStatus.COMPLETED, updatedOrder.orderStatus());
    }

    @Test
    void emptyItems_shouldHaveZeroTotalSum() {
        UUID id = UUID.randomUUID();
        Map<UUID, OrderItem> items = new HashMap<>();
        Order order = new Order(id, items);
        assertEquals(BigDecimal.ZERO, order.totalSum());
        assertEquals(OrderStatus.PROCESSING, order.orderStatus());
        assertNotEquals(OrderStatus.COMPLETED, order.orderStatus());
    }
}

