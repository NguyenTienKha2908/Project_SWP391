package com.jewelry.KiraJewelry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jewelry.KiraJewelry.models.Product;
import com.jewelry.KiraJewelry.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }

    public int findMaxProductId() {
        Integer maxId = productRepository.findMaxProductId();
        return maxId != null ? maxId : 0;
    }

    public String findMaxProductCode() {
        return productRepository.findMaxProductCode();
    }

    public int generateNewProductId() {
        return findMaxProductId() + 1;
    }

    public String generateNewProductCode() {
        String maxProductCode = findMaxProductCode();
        if (maxProductCode == null) {
            return "PO00001"; // Provide a default value if maxProductCode is null
        }
        int numericPart = Integer.parseInt(maxProductCode.substring(2));
        int newNumericPart = numericPart + 1;
        return "PO" + String.format("%05d", newNumericPart);
    }
}

