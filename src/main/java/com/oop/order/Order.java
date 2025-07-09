package com.oop.order;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public record Order(UUID id, Map<UUID, OrderItem> items, BigDecimal totalSum) {

    public Order(UUID id, Map<UUID, OrderItem> items) {
        this(id, items, null);
    }

    public Order(UUID id, Map<UUID, OrderItem> items, BigDecimal totalSum) {
        this.id = id;
        this.items = items;
        this.totalSum = calculateTotalSum(items);
    }

    private static BigDecimal calculateTotalSum(Map<UUID, OrderItem> items) {
        if (items == null) return BigDecimal.ZERO;
        return items.values().stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order withItems(Map<UUID, OrderItem> newItems) {
        return new Order(this.id, newItems, null);
    }
}
