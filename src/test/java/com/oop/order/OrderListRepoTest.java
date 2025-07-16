package com.oop.order;

import com.oop.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderListRepoTest {
    OrderItem orderItem = new OrderItem(UUID.randomUUID(),"milk",  new BigDecimal("1.25"), 40);
    OrderItem orderItem2 = new OrderItem(UUID.randomUUID(),"apple",  new BigDecimal("3"), 20);
    OrderListRepo orderListRepo;
    Order order = new Order(UUID.randomUUID(), new HashMap<>() {{
        put(orderItem.productId(), orderItem);
    }});
    Order order2 = new Order(UUID.randomUUID(), new HashMap<>() {{
        put(orderItem2.productId(), orderItem2);
    }});

    @BeforeEach
    void setUp() {
        orderListRepo = new OrderListRepo();
    }

    @Test
    void addOrder_shouldAddAOrder_toOrders() {
        orderListRepo.addOrder(order);
        Order addedOrder = orderListRepo.getOrder(order.id()).orElseThrow();
        assertEquals(addedOrder, orderListRepo.getOrder(order.id()).orElseThrow());
    }

    @Test
    void removeOrder_shouldRemoveAOrder_fromOrders() {
        orderListRepo.addOrder(order);
        assertEquals(1, orderListRepo.getOrders().size());
        orderListRepo.removeOrder(order);
        assertEquals(0, orderListRepo.getOrders().size());
    }

    @Test
    void removeOrder_shouldRemoveTheCorrectOrder_fromOrders() {
        orderListRepo.addOrder(order);
        orderListRepo.addOrder(order2);
        orderListRepo.removeOrder(order);

        List<Order> allOrders = orderListRepo.getOrders();

        assertFalse(allOrders.contains(order));
        assertTrue(allOrders.contains(order2));
    }


    @Test
    void getOrder_shouldReturnOrder_fromOrdersRepo() {
        orderListRepo.addOrder(order);
        assertEquals(order, orderListRepo.getOrder(order.id()).orElseThrow());
    }

    @Test
    void getOrder_shouldThrowAnException_ifOrderDoesNotExist() {
        UUID id = UUID.randomUUID();
        assertThrows(NoSuchElementException.class, () ->  orderListRepo.getOrder(id).orElseThrow());
    }

    @Test
    void getAllProducts() {
        assertTrue(orderListRepo.getOrders().isEmpty());
        orderListRepo.addOrder(order);
        orderListRepo.addOrder(order2);
        assertTrue(orderListRepo.getOrders().contains(order));
        assertTrue(orderListRepo.getOrders().contains(order2));
        assertEquals(2, orderListRepo.getOrders().size());
    }
}