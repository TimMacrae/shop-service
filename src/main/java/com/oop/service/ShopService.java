package com.oop.service;

import com.oop.exception.OrderWithTheIdNotFound;
import com.oop.exception.ProductWithIdNotFound;
import com.oop.exception.ProductWithTheIdIsOutOfStock;
import com.oop.order.Order;
import com.oop.order.OrderRepoInterface;
import com.oop.order.OrderStatus;
import com.oop.product.Product;
import com.oop.product.ProductRepo;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShopService {
    private final OrderRepoInterface orderRepo;
    private final ProductRepo productRepo;

    public ShopService(OrderRepoInterface orderRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public String placingOrder(Order order) {
        // Check if Products available and in stock
        order.items().forEach((productId, orderItem) ->
                checkAvailabilityAndStockQuantity(productId, orderItem.quantity())
        );

        // All products available now decrease stock and place order
        order.items().forEach((productId, orderItem) ->
                decreaseStockQuantity(productId, orderItem.quantity())
        );

        // Add order date to the order
        order = order.withOrderDate(Instant.now());
        orderRepo.addOrder(order);
        return "Order placed successfully, the total sum is: " + order.totalSum() + " 💰";
    }

    // OrderRepo
    public void addOrder(Order order) {
        orderRepo.addOrder(order);
    }

    public void removeOrder(Order order) {
        getOrder(order.id());
        orderRepo.removeOrder(order);
    }

    public Order getOrder(UUID orderId) {
        return orderRepo.getOrder(orderId).orElseThrow(()->new OrderWithTheIdNotFound(orderId));
    }

    public List<Order> getOrders() {
        return orderRepo.getOrders();
    }

    public Order updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
        Order order = getOrder(orderId);
        Order updatedOrder = order.withOrderStatus(orderStatus);
        orderRepo.removeOrder(order);
        orderRepo.addOrder(updatedOrder);
        return updatedOrder;
    }

    // ProductRepo
    public void addProduct(Product product) {
        productRepo.addProduct(product);
    }

    public Product getProduct(UUID productId) {
        return productRepo.getProduct(productId).orElseThrow(() -> new ProductWithIdNotFound(productId));
    }

    public List<Product> getAllProducts() {
        return productRepo.getAllProducts();
    }

    public List<Order> getOrdersWithOrderStatus(OrderStatus orderStatus) {
        return orderRepo.getOrders().stream().filter(order -> orderStatus.equals(order.orderStatus())).collect(Collectors.toList());
    }


    // Helper
    public void checkAvailabilityAndStockQuantity (UUID id, int quantity) {
        Product product = getProduct(id);
        if(product.stockQuantity() < quantity) throw new ProductWithTheIdIsOutOfStock(id);
    }

    public Product decreaseStockQuantity(UUID id, int quantity) {
        Product product = getProduct(id);
        int newStockQuantity = product.stockQuantity() - quantity;
        if (newStockQuantity < 0) throw new ProductWithTheIdIsOutOfStock(id);

        Product updatedProduct = new Product(product.id(), product.name(),product.description(), product.price(), newStockQuantity);
        productRepo.getProducts().put(id, updatedProduct);
        return updatedProduct;
    }

}
