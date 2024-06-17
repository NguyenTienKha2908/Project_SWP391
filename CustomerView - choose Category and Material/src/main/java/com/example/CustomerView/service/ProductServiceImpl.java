package com.example.CustomerView.service;

import com.example.CustomerView.entity.Product;
import com.example.CustomerView.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public int findMaxProductId() {
        Integer maxId = productRepository.findMaxProductId();
        return maxId != null ? maxId : 0;
    }

    @Override
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
