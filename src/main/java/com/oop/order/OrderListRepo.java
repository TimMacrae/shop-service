package com.oop.order;

import java.util.*;

public class OrderListRepo implements OrderRepoInterface{
    private final List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public Optional<Order> getOrder(UUID id) {
        return orders.stream().filter(o -> o.id().equals(id)).findFirst();
    }

    public List<Order> getOrders() {
        return orders;
    }

}
