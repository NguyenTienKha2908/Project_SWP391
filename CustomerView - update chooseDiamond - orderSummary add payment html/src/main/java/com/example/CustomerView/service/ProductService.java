package com.example.CustomerView.service;

import com.example.CustomerView.entity.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(int id);
    void saveProduct(Product product);
    void deleteProductById(int id);
    int findMaxProductId();
    String findMaxProductCode();
    int generateNewProductId();
    String generateNewProductCode();
}
