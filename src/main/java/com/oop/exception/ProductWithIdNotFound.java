package com.oop.exception;

import java.util.UUID;

public class ProductWithIdNotFound extends RuntimeException {
    public ProductWithIdNotFound(UUID id) {
        super("Product with id " + id + " not found");
    }
}
