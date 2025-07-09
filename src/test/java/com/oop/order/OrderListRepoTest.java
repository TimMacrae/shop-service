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
    Product product = new Product(UUID.randomUUID(),"milk", "high on magnesium", new BigDecimal("1.25"), 40);
    Product product2 = new Product(UUID.randomUUID(),"apple", "very sweet", new BigDecimal("3"), 20);
    OrderListRepo orderListRepo;
    Order order = new Order(UUID.randomUUID(), new HashMap<>() {{
        put(product.id(), product);
    }});
    Order order2 = new Order(UUID.randomUUID(), new HashMap<>() {{
        put(product2.id(), product2);
    }});

    @BeforeEach
    void setUp() {
        orderListRepo = new OrderListRepo();
    }

    @Test
    void addOrder_shouldAddAOrder_toOrders() {
        orderListRepo.addOrder(order);
        Order addedOrder = orderListRepo.getOrder(order.id());
        assertEquals(addedOrder, orderListRepo.getOrder(order.id()));
    }

    @Test
    void removeOrder_shouldRemoveAOrder_fromOrders() {
        orderListRepo.addOrder(order);
        assertEquals(1, orderListRepo.getOrders().size());
        orderListRepo.removeOrder(order.id());
        assertEquals(0, orderListRepo.getOrders().size());
    }

    @Test
    void removeOrder_shouldRemoveTheCorrectOrder_fromOrders() {
        orderListRepo.addOrder(order);
        orderListRepo.addOrder(order2);
        orderListRepo.removeOrder(order.id());

        List<Order> allOrders = orderListRepo.getOrders();

        assertFalse(allOrders.contains(order));
        assertTrue(allOrders.contains(order2));
    }

    @Test
    void removeOrder_shouldThrowAnException_whenOrderDoesNotExist() {
        UUID id = UUID.randomUUID();
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> orderListRepo.removeOrder(id));
        assertEquals("Order with id " + id + " does not exist", exception.getMessage());
    }

    @Test
    void getOrder_shouldReturnOrder_fromOrdersRepo() {
        orderListRepo.addOrder(order);
        assertEquals(order, orderListRepo.getOrder(order.id()));
    }

    @Test
    void getOrder_shouldThrowAnException_ifOrderDoesNotExist() {
        UUID id = UUID.randomUUID();
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> orderListRepo.getOrder(id));
        assertEquals("Order with id " + id + " does not exist", exception.getMessage());
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