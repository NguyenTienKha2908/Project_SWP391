package com.jewelry.KiraJewelry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.ProductMaterial;
import com.jewelry.KiraJewelry.repository.ProductMaterialRepository;

import java.util.List;

@Service
public class ProductMaterialService {

    @Autowired
    private ProductMaterialRepository productMaterialRepository;

    public List<ProductMaterial> getAllProductMaterials() {
        return productMaterialRepository.findAll();
    }


    public ProductMaterial getProductMaterialById(int id) {
        return productMaterialRepository.findById(id).orElse(null);
    }

    public void saveProductMaterial(ProductMaterial productMaterial) {
        productMaterialRepository.save(productMaterial);
    }

    public void deleteProductMaterialById(int id) {
        productMaterialRepository.deleteById(id);
    }
}

