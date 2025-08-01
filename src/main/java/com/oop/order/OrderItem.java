package com.oop.order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItem(UUID productId, String name, BigDecimal price, int quantity) {
}

