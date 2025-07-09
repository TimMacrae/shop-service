package com.oop.product;


import java.util.*;

public class ProductRepo {
    private final Map<UUID, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product.id(), product);
    }

    public void removeProduct(UUID id) {
        if(!products.containsKey(id)) throw new NoSuchElementException("Product with id " + id + " does not exist");
        products.remove(id);
    }

    public Product getProduct(UUID id) {
        Product product = this.products.get(id);

        if(product == null) throw new NoSuchElementException("Product with id " + id + " does not exist");
        return product;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(this.products.values());
    }


    public void checkAvailabilityAndStockQuantity (UUID id, int quantity) {
       Product product = getProduct(id);
       if(product.stockQuantity() < quantity) throw new IllegalArgumentException("Product with id " + id + " out of stock");
    }


    public Product decreaseStockQuantity(UUID id, int quantity) {
        Product product = getProduct(id);
        int newStockQuantity = product.stockQuantity() - quantity;
        if (newStockQuantity < 0) throw new IllegalArgumentException("Product with id " + id + " out of stock");

        Product updatedProduct = new Product(product.id(), product.name(),product.description(), product.price(), newStockQuantity);
        products.put(id, updatedProduct);
        return updatedProduct;
    }
}
