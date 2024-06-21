package com.jewelry.KiraJewelry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.ProductMaterial;
import com.jewelry.KiraJewelry.models.ProductMaterialId;
import com.jewelry.KiraJewelry.repository.ProductMaterialRepository;

@Service
public class ProductMaterialService {
    @Autowired
    private ProductMaterialRepository productMaterialRepository;

    public void deleteProductMaterialById(int productId, int materialId) {
        productMaterialRepository.deleteById(new ProductMaterialId(productId, materialId));
    }

    public void saveProductMaterial(ProductMaterial productMaterial) {
        productMaterialRepository.save(productMaterial);
    }

    public ProductMaterial getProductMaterialByProductId(int productId) {
        return productMaterialRepository.findProductMaterialByProductId(productId);
    }
}
