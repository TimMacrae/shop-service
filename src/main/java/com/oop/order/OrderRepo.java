package com.oop.order;

import java.util.*;

public class OrderRepo implements OrderRepoInterface{
    private final Map<UUID, Order> orders = new HashMap<>();

    public void addOrder(Order order) {
        orders.put(order.id(), order);
    }

    public void removeOrder(Order order) {
        orders.remove(order.id());
    }

    public Optional<Order> getOrder(UUID id) {
        return Optional.ofNullable( orders.get(id));
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders.values());
    }

}
