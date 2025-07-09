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
        Product product = new Product(UUID.randomUUID(), "apple", "very sweet", BigDecimal.ONE, 30);
        Map<UUID, Product> products = new HashMap<>();
        products.put(product.id(), product);

        Order order = new Order(orderId,products );
        assertEquals(orderId,order.id());
        assertEquals(products,order.products());

    }

}