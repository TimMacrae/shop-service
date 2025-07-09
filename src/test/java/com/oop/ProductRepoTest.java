package com.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {
    ProductRepo productRepo;
    Product product = new Product(UUID.randomUUID(),"milk", "high on magnesium", new BigDecimal("1.25"), 40);
    Product product2 = new Product(UUID.randomUUID(),"apple", "very sweet", new BigDecimal("3"), 20);
    Product product3 = new Product(UUID.randomUUID(),"banana", "bio banana", new BigDecimal("1"), 60);

    @BeforeEach
    void setUp() {
        productRepo = new ProductRepo();
    }

    @Test
    void addProduct_shouldAddAProduct_toProducts() {
        productRepo.addProduct(product);
        Product addedProduct = productRepo.getProduct(product.id());
        assertEquals(addedProduct, productRepo.getProduct(product.id()));
    }

    @Test
    void removeProduct_shouldRemoveAProduct_fromProducts() {
        productRepo.addProduct(product);
        assertEquals(1, productRepo.getAllProducts().size());
        productRepo.removeProduct(product.id());
        assertEquals(0, productRepo.getAllProducts().size());
    }

    @Test
    void removeProduct_shouldRemoveTheCorrectProduct_fromProducts() {
        productRepo.addProduct(product);
        productRepo.addProduct(product2);
        productRepo.addProduct(product3);
        productRepo.removeProduct(product.id());
        assertEquals(2, productRepo.getAllProducts().size());

        List<Product> allProducts = productRepo.getAllProducts();
        assertFalse(allProducts.contains(product));
        assertTrue(allProducts.contains(product2));
        assertTrue(allProducts.contains(product3));
    }

    @Test
    void removeProduct_shouldThrowAnException_whenProductDoesNotExist() {
        UUID id = UUID.randomUUID();
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> productRepo.removeProduct(id));
        assertEquals("Product with id " + id + " does not exist", exception.getMessage());
    }

    @Test
    void getProduct_shouldReturnProduct_fromProductRepo() {
        productRepo.addProduct(product);
        assertEquals(product, productRepo.getProduct(product.id()));
    }

    @Test
    void getProduct_shouldThrowAnException_ifProductDoesNotExist() {
        UUID id = UUID.randomUUID();
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> productRepo.getProduct(id));
        assertEquals("Product with id " + id + " does not exist", exception.getMessage());
    }

    @Test
    void getAllProducts() {
        assertTrue(productRepo.getAllProducts().isEmpty());
        productRepo.addProduct(product);
        productRepo.addProduct(product2);
        assertTrue(productRepo.getAllProducts().contains(product));
        assertTrue(productRepo.getAllProducts().contains(product2));
        assertEquals(2, productRepo.getAllProducts().size());
    }
}