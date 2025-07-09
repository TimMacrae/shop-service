package com.oop.order;

import java.util.*;

public class OrderRepo {
    private final Map<UUID, Order> orders = new HashMap<>();

    public void addOrder(Order order) {
        orders.put(order.id(), order);
    }

    public void removeOrder(UUID id) {
        if(!orders.containsKey(id)) throw new NoSuchElementException("Order with id " + id + " does not exist");
        orders.remove(id);
    }

    public Order getOrder(UUID id) {
        if(!orders.containsKey(id)) throw new NoSuchElementException("Order with id " + id + " does not exist");
        return orders.get(id);
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders.values());
    }

}
