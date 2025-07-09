package com.oop.order;

import java.util.Map;
import java.util.UUID;

public record Order(UUID id, Map<UUID, OrderItem> items) {
}
