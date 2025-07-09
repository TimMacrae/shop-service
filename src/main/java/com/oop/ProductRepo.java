package com.oop;

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

}
