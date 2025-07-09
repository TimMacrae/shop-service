package com.oop;

import com.oop.order.Order;
import com.oop.order.OrderItem;
import com.oop.order.OrderRepo;
import com.oop.product.Product;
import com.oop.product.ProductRepo;
import com.oop.service.ShopService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;


public class Main {
    public static void main(String[] args) {
        OrderRepo orderRepo = new OrderRepo();
        ProductRepo productRepo = new ProductRepo();
        ShopService shopService = new ShopService(orderRepo, productRepo);


        // Create some products
        Product productTomato = new Product(UUID.randomUUID(), "tomato", "best tomato", new BigDecimal("1.25"), 100);
        Product productApple = new Product(UUID.randomUUID(), "apple", "best apple", new BigDecimal("1.75"), 150);
        Product productBanana = new Product(UUID.randomUUID(), "banana", "best banana", new BigDecimal("2.00"), 80);
        productRepo.addProduct(productTomato);
        productRepo.addProduct(productApple);
        productRepo.addProduct(productBanana);

        // Create some OrderItems
        OrderItem orderItemTomato = new OrderItem(productBanana.id(), productTomato.name(), productTomato.price(), 5);
        OrderItem orderItemApple = new OrderItem(productApple.id(), productApple.name(), productApple.price(), 2);
        OrderItem orderItemBanana = new OrderItem(productBanana.id(), productBanana.name(), productBanana.price(), 90);

        // Create some Orders
        Order order = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItemTomato.productId(), orderItemTomato);
            put(orderItemApple.productId(), orderItemApple);
        }});

        Order orderTwo = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItemBanana.productId(), orderItemBanana);
        }});

        System.out.println("ORDER: " + order.toString());


        // Place orders
        var placedOrder = shopService.placingOrder(order);
        System.out.println(placedOrder);
        var placedOrderTwo = shopService.placingOrder(orderTwo);
        System.out.println(placedOrderTwo);


    }
}