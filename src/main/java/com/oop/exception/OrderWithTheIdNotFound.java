package com.oop.exception;

import java.util.UUID;

public class OrderWithTheIdNotFound extends RuntimeException {
    public OrderWithTheIdNotFound(UUID id) {
        super("Order with id " + id + " not found");
    }
}
