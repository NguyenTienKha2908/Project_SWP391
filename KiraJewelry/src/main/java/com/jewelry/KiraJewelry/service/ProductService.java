package com.jewelry.KiraJewelry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.Product;
import com.jewelry.KiraJewelry.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void deleteProductById(int productId) {
        productRepository.deleteById(productId);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}
