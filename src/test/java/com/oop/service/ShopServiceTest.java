package com.oop.service;

import com.oop.exception.OrderWithTheIdNotFound;
import com.oop.exception.ProductWithIdNotFound;
import com.oop.exception.ProductWithTheIdIsOutOfStock;
import com.oop.order.*;
import com.oop.product.Product;
import com.oop.product.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {
    ProductRepo productRepo = new ProductRepo();
    OrderRepo orderRepo = new OrderRepo();
    OrderListRepo orderListRepo = new OrderListRepo();
    ShopService shopService;

    Product product;
    Product product2;

    OrderItem orderItem;
    OrderItem orderItem2;

    // Create some Orders
    Order order;
    Order orderWithStatusProgress;
    Order orderWithStatusInDelivery;
    Order orderWithStatusCompleted;


    @BeforeEach
    void setUp() {
        shopService = new ShopService(orderRepo, productRepo);
        product = new Product(UUID.randomUUID(), "milk", "high on magnesium", new BigDecimal("1.25"), 40);
        product2 = new Product(UUID.randomUUID(), "apple", "very sweet", new BigDecimal("3"), 20);

        orderItem = new OrderItem(product.id(), product.name(), product.price(), 10);
        orderItem2 = new OrderItem(product2.id(), product2.name(), product2.price(), 5);

        productRepo.addProduct(product);
        productRepo.addProduct(product2);

        order = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItem.productId(), orderItem);
            put(orderItem2.productId(), orderItem2);
        }});

        orderWithStatusProgress = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItem.productId(), orderItem);
        }});

        orderWithStatusInDelivery = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItem.productId(), orderItem);
            put(orderItem2.productId(), orderItem2);
        }}, null, OrderStatus.IN_DELIVERY);

        orderWithStatusCompleted = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItem.productId(), orderItem);
        }}, null, OrderStatus.COMPLETED);

    }

    @Test
    void placingOrder_success() {
        Order order = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItem.productId(), orderItem);
            put(orderItem2.productId(), orderItem2);
        }});

        String placedOrder = shopService.placingOrder(order);
        assertEquals("Order placed successfully, the total sum is: 27.50 \uD83D\uDCB0", placedOrder);
    }

    @Test
    void placingOrder_success_orderListRepo() {
        shopService = new ShopService(orderListRepo, productRepo);
        Order order = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItem.productId(), orderItem);
            put(orderItem2.productId(), orderItem2);
        }});

        String placedOrder = shopService.placingOrder(order);
        assertEquals("Order placed successfully, the total sum is: 27.50 \uD83D\uDCB0", placedOrder);
    }

    @Test
    void placingOrder_shouldThrowAnException_productNotFound() {
        UUID id = UUID.randomUUID();
        OrderItem orderItemNotAvailable = new OrderItem(id, "mango", new BigDecimal("3"), 2);
        Order order = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItem.productId(), orderItem);
            put(orderItemNotAvailable.productId(), orderItemNotAvailable);
        }});

        ProductWithIdNotFound exception = assertThrows(ProductWithIdNotFound.class, () -> shopService.placingOrder(order));
        assertEquals("Product with id " + id + " not found", exception.getMessage());
    }

    @Test
    void placingOrder_shouldThrowAnException_productNotFound_orderListRepo() {
        shopService = new ShopService(orderListRepo, productRepo);
        UUID id = UUID.randomUUID();
        OrderItem orderItemNotAvailable = new OrderItem(id, "mango", new BigDecimal("3"), 2);
        Order order = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItem.productId(), orderItem);
            put(orderItemNotAvailable.productId(), orderItemNotAvailable);
        }});

        ProductWithIdNotFound exception = assertThrows(ProductWithIdNotFound.class, () -> shopService.placingOrder(order));
        assertEquals("Product with id " + id + " not found", exception.getMessage());
    }

    @Test
    void placingOrder_shouldThrowAnException_ifStockQuantityIsTooLow() {
        OrderItem orderItemTooHighQuantity = new OrderItem(product2.id(), product2.name(), product2.price(), 100);
        Order order = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItem.productId(), orderItem);
            put(orderItemTooHighQuantity.productId(), orderItemTooHighQuantity);
        }});

        ProductWithTheIdIsOutOfStock exception = assertThrows(ProductWithTheIdIsOutOfStock.class, () -> shopService.placingOrder(order));
        assertEquals("Product with id " + orderItemTooHighQuantity.productId() + " out of stock", exception.getMessage());
    }

    @Test
    void placingOrder_shouldThrowAnException_ifStockQuantityIsTooLow_orderListRepo() {
        OrderItem orderItemTooHighQuantity = new OrderItem(product2.id(), product2.name(), product2.price(), 100);
        Order order = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItem.productId(), orderItem);
            put(orderItemTooHighQuantity.productId(), orderItemTooHighQuantity);
        }});

        ProductWithTheIdIsOutOfStock exception = assertThrows(ProductWithTheIdIsOutOfStock.class, () -> shopService.placingOrder(order));
        assertEquals("Product with id " + orderItemTooHighQuantity.productId() + " out of stock", exception.getMessage());
    }


    @Test
    void addOrder_shouldAddAOrder_toOrders() {
        shopService.addOrder(order);
        Order addedOrder = shopService.getOrder(order.id());
        assertEquals(addedOrder, shopService.getOrder(order.id()));
    }

    @Test
    void removeOrder_shouldRemoveAOrder_fromOrders() {
        shopService.addOrder(order);
        assertEquals(1, shopService.getOrders().size());
        shopService.removeOrder(order);
        assertEquals(0, shopService.getOrders().size());
    }

    @Test
    void getOrder_shouldReturnOrder_fromOrdersRepo() {
        shopService.addOrder(order);
        assertEquals(order, shopService.getOrder(order.id()));
    }

    @Test
    void getOrder_shouldThrowAnException_ifOrderDoesNotExist() {
        UUID id = UUID.randomUUID();
        OrderWithTheIdNotFound exception = assertThrows(OrderWithTheIdNotFound.class, () -> shopService.getOrder(id));
        assertEquals("Order with id " + id + " not found", exception.getMessage());
    }

    @Test
    void getOrders_shouldReturnOrders_fromOrderRepo() {
        Order order2 = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItem.productId(), orderItem);
            put(orderItem2.productId(), orderItem2);
        }});
        shopService.addOrder(order);
        shopService.addOrder(order2);
        assertEquals(2, shopService.getOrders().size());
    }

    @Test
    void getProduct_shouldReturnProduct_fromProductRepo() {
        assertEquals(product, shopService.getProduct(product.id()));
    }

    @Test
    void getAllProducts() {
        List<Product> products = shopService.getAllProducts();
        assertEquals(2, products.size());
        products.forEach(product -> assertEquals(product, productRepo.getProduct(product.id()).orElseThrow()));
    }

    @Test
    void addProduct_shouldAddProduct_toProductRepo() {
        assertEquals(2, shopService.getAllProducts().size());
        shopService.addProduct(product);
        assertEquals(2, shopService.getAllProducts().size());
    }

    @Test
    void getOrdersWithOrderStatus_shouldReturnOrders_withOrderStatusOrEmptyList() {
        assertEquals(0, shopService.getOrdersWithOrderStatus(OrderStatus.PROCESSING).size());
        assertEquals(0, shopService.getOrdersWithOrderStatus(OrderStatus.IN_DELIVERY).size());
        assertEquals(0, shopService.getOrdersWithOrderStatus(OrderStatus.COMPLETED).size());

        shopService.placingOrder(order);
        shopService.placingOrder(orderWithStatusProgress);
        shopService.placingOrder(orderWithStatusCompleted);
        shopService.placingOrder(orderWithStatusInDelivery);

        assertEquals(2, shopService.getOrdersWithOrderStatus(OrderStatus.PROCESSING).size());
        assertEquals(1, shopService.getOrdersWithOrderStatus(OrderStatus.IN_DELIVERY).size());
        assertEquals(1, shopService.getOrdersWithOrderStatus(OrderStatus.COMPLETED).size());
    }

    @Test
    void checkAvailabilityAndStockQuantity_shouldThrowAnException_whenProductDoesNotExist() {
        UUID id = UUID.randomUUID();
        ProductWithIdNotFound exception = assertThrows(ProductWithIdNotFound.class, () -> shopService.checkAvailabilityAndStockQuantity(id, 30));
        assertEquals("Product with id " + id + " not found", exception.getMessage());
    }

    @Test
    void checkAvailabilityAndStockQuantity_shouldThrowAnException_whenStockQuantityIsNotEnough() {
        productRepo.addProduct(product);
        ProductWithTheIdIsOutOfStock exception = assertThrows(ProductWithTheIdIsOutOfStock.class, () -> shopService.checkAvailabilityAndStockQuantity(product.id(), 50));
        assertEquals("Product with id " + product.id() + " out of stock", exception.getMessage());
    }

    @Test
    void decreaseStockQuantity_shouldThrowAnException_whenProductDoesNotExist() {
        UUID id = UUID.randomUUID();
        ProductWithIdNotFound exception = assertThrows(ProductWithIdNotFound.class, () -> shopService.decreaseStockQuantity(id, 30));
        assertEquals("Product with id " + id + " not found", exception.getMessage());
    }

    @Test
    void decreaseStockQuantity_shouldThrowAnException_whenStockQuantityIsNotEnough() {
        productRepo.addProduct(product);
        ProductWithTheIdIsOutOfStock exception = assertThrows(ProductWithTheIdIsOutOfStock.class, () -> shopService.checkAvailabilityAndStockQuantity(product.id(), 100));
        assertEquals("Product with id " + product.id() + " out of stock", exception.getMessage());
    }


    @Test
    void decreaseStockQuantity_shouldDecreaseStockQuantity_whenStockQuantityIsEnough() {
        productRepo.addProduct(product);
        Product decreasedProduct = shopService.decreaseStockQuantity(product.id(), 30);
        Product updatedProduct = shopService.getProduct(product.id());
        assertEquals(decreasedProduct, updatedProduct);

    }

    @Test
    void updateOrderStatus_ShouldUpdateOrderStatusToGivenStatus() {
        shopService.placingOrder(order);
        assertEquals(OrderStatus.PROCESSING, shopService.getOrder(order.id()).orderStatus());

        shopService.updateOrderStatus(order.id(), OrderStatus.IN_DELIVERY);
        assertEquals(OrderStatus.IN_DELIVERY, shopService.getOrder(order.id()).orderStatus());

        shopService.updateOrderStatus(order.id(), OrderStatus.COMPLETED);
        assertEquals(OrderStatus.COMPLETED, shopService.getOrder(order.id()).orderStatus());
    }

    @Test
    void updateOrderStatus_ShouldThrowAnException_whenOrderIdDoesNotExist() {
        assertThrows(OrderWithTheIdNotFound.class, () -> shopService.updateOrderStatus(UUID.randomUUID(), OrderStatus.PROCESSING));
    }

}