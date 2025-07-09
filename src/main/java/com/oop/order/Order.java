package com.oop.order;

import com.oop.product.Product;

import java.util.Map;
import java.util.UUID;

public record Order(UUID id, Map<UUID, Product> products) {
}
