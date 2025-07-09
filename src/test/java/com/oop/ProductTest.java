package com.oop;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void createProduct_shouldHaveTheCorrectValues() {
        UUID id = UUID.randomUUID();
        String name = "Test Product";
        String description = "Test Description";
        BigDecimal price = BigDecimal.TEN;
        int stockQuantity = 50;

        Product product = new Product(id, name, description, price, stockQuantity);

        assertEquals(id, product.id());
        assertEquals(name, product.name());
        assertEquals(description, product.description());
        assertEquals(price, product.price());
        assertEquals(stockQuantity, product.stockQuantity());
    }


}