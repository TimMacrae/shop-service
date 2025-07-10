package com.oop.service;

import com.oop.order.Order;
import com.oop.order.OrderItem;
import com.oop.order.OrderListRepo;
import com.oop.order.OrderRepo;
import com.oop.product.Product;
import com.oop.product.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static javax.swing.UIManager.put;
import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {
    ProductRepo productRepo =  new ProductRepo();
    OrderRepo orderRepo  = new OrderRepo();
    OrderListRepo orderListRepo  = new OrderListRepo();
    ShopService shopService;

    Product product;
    Product product2;

    OrderItem orderItem;
    OrderItem orderItem2;

    // Create some Orders
    Order order;


    @BeforeEach
    void setUp() {
         product = new Product(UUID.randomUUID(),"milk", "high on magnesium", new BigDecimal("1.25"), 40);
         product2 = new Product(UUID.randomUUID(),"apple", "very sweet", new BigDecimal("3"), 20);

         orderItem = new OrderItem(product.id(),product.name(),  product.price(), 10);
         orderItem2 = new OrderItem(product2.id(),product2.name(),  product2.price(), 5);

         productRepo.addProduct(product);
         productRepo.addProduct(product2);

         order = new Order(UUID.randomUUID(), new HashMap<>() {{
             put(orderItem.productId(), orderItem);
             put(orderItem2.productId(), orderItem2);
         }});
    }

    @Test
    void placingOrder_success() {
        shopService = new ShopService(orderRepo, productRepo);
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
        shopService = new ShopService(orderRepo, productRepo);
        UUID id = UUID.randomUUID();
        OrderItem orderItemNotAvailable = new OrderItem(id, "mango", new BigDecimal("3"), 2);
        Order order = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItem.productId(), orderItem);
            put(orderItemNotAvailable.productId(), orderItemNotAvailable);
        }});

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> shopService.placingOrder(order));
        assertEquals("Product with id " + id + " does not exist", exception.getMessage());
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

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> shopService.placingOrder(order));
        assertEquals("Product with id " + id + " does not exist", exception.getMessage());
    }

    @Test
    void placingOrder_shouldThrowAnException_ifStockQuantityIsTooLow() {
        shopService = new ShopService(orderRepo, productRepo);
        OrderItem orderItemTooHighQuantity = new OrderItem(product2.id(),product2.name(),  product2.price(), 100);
        Order order = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItem.productId(), orderItem);
            put(orderItemTooHighQuantity.productId(), orderItemTooHighQuantity);
        }});

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> shopService.placingOrder(order));
        assertEquals("Product with id " + orderItemTooHighQuantity.productId() + " out of stock", exception.getMessage());
    }

    @Test
    void placingOrder_shouldThrowAnException_ifStockQuantityIsTooLow_orderListRepo() {
        shopService = new ShopService(orderListRepo, productRepo);
        OrderItem orderItemTooHighQuantity = new OrderItem(product2.id(),product2.name(),  product2.price(), 100);
        Order order = new Order(UUID.randomUUID(), new HashMap<>() {{
            put(orderItem.productId(), orderItem);
            put(orderItemTooHighQuantity.productId(), orderItemTooHighQuantity);
        }});

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> shopService.placingOrder(order));
        assertEquals("Product with id " + orderItemTooHighQuantity.productId() + " out of stock", exception.getMessage());
    }


    @Test
    void addOrder_shouldAddAOrder_toOrders() {
        shopService = new ShopService(orderRepo, productRepo);
        shopService.addOrder(order);
        Order addedOrder = shopService.getOrder(order.id());
        assertEquals(addedOrder, shopService.getOrder(order.id()));
    }

    @Test
    void removeOrder_shouldRemoveAOrder_fromOrders() {
        shopService = new ShopService(orderRepo, productRepo);
        shopService.addOrder(order);
        assertEquals(1, shopService.getOrders().size());
        shopService.removeOrder(order.id());
        assertEquals(0, shopService.getOrders().size());
    }

    @Test
    void getOrder_shouldReturnOrder_fromOrdersRepo() {
        shopService = new ShopService(orderRepo, productRepo);
        shopService.addOrder(order);
        assertEquals(order, shopService.getOrder(order.id()));
    }

    @Test
    void getOrder_shouldThrowAnException_ifOrderDoesNotExist() {
        shopService = new ShopService(orderRepo, productRepo);
        UUID id = UUID.randomUUID();
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> shopService.getOrder(id));
        assertEquals("Order with id " + id + " does not exist", exception.getMessage());
    }

    @Test
    void getOrders_shouldReturnOrders_fromOrderRepo() {
        shopService = new ShopService(orderRepo, productRepo);
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
        shopService = new ShopService(orderRepo, productRepo);
        assertEquals(product, shopService.getProduct(product.id()));
    }

    @Test
    void getAllProducts() {
        shopService = new ShopService(orderRepo, productRepo);
        List<Product> products = shopService.getAllProducts();
        assertEquals(2, products.size());
        products.forEach(product -> assertEquals(product, productRepo.getProduct(product.id())));
    }

    @Test
    void addProduct_shouldAddProduct_toProductRepo() {
        shopService = new ShopService(orderRepo, productRepo);
        assertEquals(2, shopService.getAllProducts().size());
        shopService.addProduct(product);
        assertEquals(2, shopService.getAllProducts().size());
    }


}