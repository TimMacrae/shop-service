package com.oop.order;

import com.oop.product.Product;
import com.oop.product.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepoTest {

    OrderItem orderItem = new OrderItem(UUID.randomUUID(),"milk",  new BigDecimal("1.25"), 40);
    OrderItem orderItem2 = new OrderItem(UUID.randomUUID(),"apple",  new BigDecimal("3"), 20);
    OrderRepo orderRepo;
    Order order = new Order(UUID.randomUUID(), new HashMap<>() {{
        put(orderItem.productId(), orderItem);
    }});
    Order order2 = new Order(UUID.randomUUID(), new HashMap<>() {{
        put(orderItem2.productId(), orderItem2);
    }});

    @BeforeEach
    void setUp() {
        orderRepo = new OrderRepo();
    }

    @Test
    void addOrder_shouldAddAOrder_toOrders() {
        orderRepo.addOrder(order);
        Order addedOrder = orderRepo.getOrder(order.id());
        assertEquals(addedOrder, orderRepo.getOrder(order.id()));
    }

    @Test
    void removeOrder_shouldRemoveAOrder_fromOrders() {
        orderRepo.addOrder(order);
        assertEquals(1, orderRepo.getOrders().size());
        orderRepo.removeOrder(order.id());
        assertEquals(0, orderRepo.getOrders().size());
    }

    @Test
    void removeOrder_shouldRemoveTheCorrectOrder_fromOrders() {
        orderRepo.addOrder(order);
        orderRepo.addOrder(order2);
        orderRepo.removeOrder(order.id());

        List<Order> allOrders = orderRepo.getOrders();

        assertFalse(allOrders.contains(order));
        assertTrue(allOrders.contains(order2));
    }

    @Test
    void removeOrder_shouldThrowAnException_whenOrderDoesNotExist() {
        UUID id = UUID.randomUUID();
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> orderRepo.removeOrder(id));
        assertEquals("Order with id " + id + " does not exist", exception.getMessage());
    }

    @Test
    void getOrder_shouldReturnOrder_fromOrdersRepo() {
        orderRepo.addOrder(order);
        assertEquals(order, orderRepo.getOrder(order.id()));
    }

    @Test
    void getOrder_shouldThrowAnException_ifOrderDoesNotExist() {
        UUID id = UUID.randomUUID();
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> orderRepo.getOrder(id));
        assertEquals("Order with id " + id + " does not exist", exception.getMessage());
    }

    @Test
    void getAllProducts() {
        assertTrue(orderRepo.getOrders().isEmpty());
        orderRepo.addOrder(order);
        orderRepo.addOrder(order2);
        assertTrue(orderRepo.getOrders().contains(order));
        assertTrue(orderRepo.getOrders().contains(order2));
        assertEquals(2, orderRepo.getOrders().size());
    }

}