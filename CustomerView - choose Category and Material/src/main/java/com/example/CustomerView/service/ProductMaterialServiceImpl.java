package com.example.CustomerView.service;

import com.example.CustomerView.entity.ProductMaterial;
import com.example.CustomerView.repository.ProductMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductMaterialServiceImpl implements ProductMaterialService {

    @Autowired
    private ProductMaterialRepository productMaterialRepository;

    @Override
    public List<ProductMaterial> getAllProductMaterials() {
        return productMaterialRepository.findAll();
    }

    @Override
    public ProductMaterial getProductMaterialById(int id) {
        return productMaterialRepository.findById(id).orElse(null);
    }

    @Override
    public void saveProductMaterial(ProductMaterial productMaterial) {
        productMaterialRepository.save(productMaterial);
    }

    @Override
    public void deleteProductMaterialById(int id) {
        productMaterialRepository.deleteById(id);
    }
}
