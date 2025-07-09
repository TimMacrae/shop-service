package com.oop.order;

import java.util.*;

public class OrderListRepo implements OrderRepoInterface{
    private final List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(UUID id) {
        Order order = orders.stream().filter(o -> o.id().equals(id)).findFirst().orElse(null);
        if(order == null) throw new NoSuchElementException("Order with id " + id + " does not exist");
        orders.remove(order);
    }

    public Order getOrder(UUID id) {
        Order order = orders.stream().filter(o -> o.id().equals(id)).findFirst().orElse(null);
        if(order == null) throw new NoSuchElementException("Order with id " + id + " does not exist");
        return order;
    }

    public List<Order> getOrders() {
        return orders;
    }

}
