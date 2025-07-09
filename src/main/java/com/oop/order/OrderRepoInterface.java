package com.oop.order;

import java.util.List;
import java.util.UUID;

public interface OrderRepoInterface {
    public Order getOrder(UUID id);
    public List<Order> getOrders();
    public void addOrder(Order order);
    public void removeOrder(UUID id);
}
