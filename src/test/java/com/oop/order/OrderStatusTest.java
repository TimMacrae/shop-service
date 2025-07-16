package com.oop.order;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {
    @Test
    void orderStatus_testEnumValues() {
        OrderStatus[] statuses = OrderStatus.values();
        assertArrayEquals(
                new OrderStatus[]{OrderStatus.PROCESSING, OrderStatus.IN_DELIVERY, OrderStatus.COMPLETED},
                statuses
        );
    }

    @Test
    void orderStatus_testValueOf() {
        assertEquals(OrderStatus.PROCESSING, OrderStatus.valueOf("PROCESSING"));
        assertEquals(OrderStatus.IN_DELIVERY, OrderStatus.valueOf("IN_DELIVERY"));
        assertEquals(OrderStatus.COMPLETED, OrderStatus.valueOf("COMPLETED"));
    }

    @Test
    void orderStatus_testInvalidValueOfThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> OrderStatus.valueOf("INVALID"));
    }
}