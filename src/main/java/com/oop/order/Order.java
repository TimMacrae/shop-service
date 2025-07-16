package com.oop.order;

import lombok.With;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@With
public record Order(UUID id, Map<UUID, OrderItem> items, BigDecimal totalSum, OrderStatus orderStatus, Instant orderDate) {

    public Order(UUID id, Map<UUID, OrderItem> items) {
        this(id, items, calculateTotalSum(items), OrderStatus.PROCESSING, null);
    }

    private static BigDecimal calculateTotalSum(Map<UUID, OrderItem> items) {
        if (items == null) return BigDecimal.ZERO;
        return items.values().stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order withItems(Map<UUID, OrderItem> newItems) {
        return new Order(this.id, newItems, calculateTotalSum(newItems), this.orderStatus, this.orderDate);
    }
}
