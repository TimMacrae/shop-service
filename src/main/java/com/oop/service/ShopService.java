package com.oop.service;

import com.oop.order.Order;
import com.oop.order.OrderRepoInterface;
import com.oop.product.Product;
import com.oop.product.ProductRepo;

import java.util.List;
import java.util.UUID;

public class ShopService implements OrderRepoInterface {
    private final OrderRepoInterface orderRepo;
    private final ProductRepo productRepo;

    public ShopService(OrderRepoInterface orderRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public String placingOrder(Order order) {
        // Check if Products available and in stock
        for (var item : order.items().entrySet()) {
            var productId = item.getKey();
            var orderItem = item.getValue();
            // This will throw if not available or not enough in stock
            productRepo.checkAvailabilityAndStockQuantity(productId, orderItem.quantity());
        }

        // All products available now decrease stock and place order
        for (var item : order.items().entrySet()) {
            var productId = item.getKey();
            var orderItem = item.getValue();
            productRepo.decreaseStockQuantity(productId, orderItem.quantity());
        }

        orderRepo.addOrder(order);
        return "Order placed successfully, the total sum is: " + order.totalSum() + " 💰";
    }

    public void addOrder(Order order) {
        orderRepo.addOrder(order);
    }

    public Order getOrder(UUID orderId) {
        return orderRepo.getOrder(orderId);
    }

    public List<Order> getOrders() {
        return orderRepo.getOrders();
    }

    public void removeOrder(UUID orderId) {
        orderRepo.removeOrder(orderId);
    }

    public Product getProduct(UUID productId) {
       return productRepo.getProduct(productId);
    }

    public List<Product> getAllProducts() {
        return productRepo.getAllProducts();
    }

    public void addProduct(Product product) {
        productRepo.addProduct(product);
    }

}
