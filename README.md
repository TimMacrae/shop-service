# Shop Service Recap Project 🛒

This project is a simple Java backend for managing products and orders in a shop, designed as a recap and learning exercise. It demonstrates core object-oriented programming concepts, Java records, exception handling, and basic repository patterns.

## ✨ Features

- `Product Management`: Add, remove, and retrieve products with details like name, description, price, and stock quantity.
- `Order Management`: Create orders containing multiple items, each with its own quantity and price.
- `Stock Handling`: Automatically checks and updates product stock when orders are placed.
- `Immutability`: Uses Java records for immutable data structures (Product, Order, OrderItem).
- `️ Repositories`: In-memory repositories for products and orders (both map-based and list-based implementations).
- `️ Service Layer`: Business logic for placing orders, including validation and stock updates.
- `️ Exception Handling`: Throws meaningful exceptions for missing products, insufficient stock, and invalid operations.
- `Unit Tests`: Comprehensive JUnit tests for all major components.

## 📁 Structure

- `product` — Product domain and repository
- `order` — Order domain, order item, and repositories
- `service` — ShopService with business logic
- `Main.java` — Example usage and entry point
- `test/` — JUnit tests for all modules

## 🚀 How to Run

1.  Clone the repository.
2. ️ Build with Maven or your preferred Java build tool.
3. ️ Run `Main.java` to see example usage.
4.  Run tests with `mvn test` or your IDE's test runner.

## 💡 Example Usage

```
ProductRepo productRepo = new ProductRepo();
OrderRepo orderRepo = new OrderRepo();
ShopService shopService = new ShopService(orderRepo, productRepo);

Product product = new Product(UUID.randomUUID(), "apple", "fresh apple", new BigDecimal("2.00"), 50);
productRepo.addProduct(product);

OrderItem orderItem = new OrderItem(product.id(), product.name(), product.price(), 5);
Order order = new Order(UUID.randomUUID(), Map.of(orderItem.productId(), orderItem));

String result = shopService.placingOrder(order);
System.out.println(result); // Order placed successfully, the total sum is: ...
```

## 🧪 Tests

- ProductRepoTest
- ProductTest
- OrderRepoTest
- OrderListRepoTest
- OrderTest
- OrderItemTest
- ShopServiceTest

## 📜 License

This project is for educational purposes.
