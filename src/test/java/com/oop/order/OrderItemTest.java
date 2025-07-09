package com.oop.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void createOrderItem_shouldHaveTheCorrectValues(){
        UUID productId = UUID.randomUUID();
        String orderItemName = "orange";
        BigDecimal price = new BigDecimal("1.00");
        int quantity = 5;

        OrderItem orderItem = new OrderItem(productId,orderItemName,price,quantity);
        assertEquals(productId,orderItem.productId());
        assertEquals(orderItemName,orderItem.name());
        assertEquals(price,orderItem.price());
        assertEquals(quantity,orderItem.quantity());
    }

}