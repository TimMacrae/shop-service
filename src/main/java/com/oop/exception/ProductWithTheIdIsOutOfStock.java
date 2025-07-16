package com.oop.exception;

import java.util.UUID;

public class ProductWithTheIdIsOutOfStock extends RuntimeException {
    public ProductWithTheIdIsOutOfStock(UUID id) {
        super("Product with id " + id + " out of stock");
    }
}
