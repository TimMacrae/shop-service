package com.oop.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepoInterface {
    public Optional<Order> getOrder(UUID id);
    public List<Order> getOrders();
    public void addOrder(Order order);
    public void removeOrder(Order order);
}
