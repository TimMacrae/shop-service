package com.oop.product;


import com.oop.exception.ProductWithIdNotFound;
import com.oop.exception.ProductWithTheIdIsOutOfStock;
import lombok.Getter;

import java.util.*;

@Getter
public class ProductRepo {
    private final Map<UUID, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product.id(), product);
    }

    public void removeProduct(UUID id) {
        if(!products.containsKey(id)) throw new ProductWithIdNotFound(id);
        products.remove(id);
    }

    public Optional<Product> getProduct(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(this.products.values());
    }

}
