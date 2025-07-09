package com.oop.order;

import com.oop.product.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void createOrder_shouldHaveTheCorrectValues() {
        UUID orderId = UUID.randomUUID();
        OrderItem orderItem = new OrderItem(UUID.randomUUID(), "apple",  BigDecimal.ONE,  30);
        Map<UUID, OrderItem> items = new HashMap<>();
        items.put(orderItem.productId(), orderItem);

        Order order = new Order(orderId, items );
        assertEquals(orderId,order.id());
        assertEquals(items,order.items());

    }

}