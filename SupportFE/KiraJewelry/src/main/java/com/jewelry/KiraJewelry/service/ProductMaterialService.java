package com.jewelry.KiraJewelry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.ProductMaterial;
import com.jewelry.KiraJewelry.models.ProductMaterialId;
import com.jewelry.KiraJewelry.repository.ProductMaterialRepository;

import java.util.List;

@Service
public class ProductMaterialService {

    @Autowired
    private ProductMaterialRepository productMaterialRepository;

    public List<ProductMaterial> getAllProductMaterials() {
        return productMaterialRepository.findAll();
    }

    public List<ProductMaterial> getListProductMaterialByProductId(int productId) {
        return productMaterialRepository.findListProductMaterialByProductId(productId);
    }

    public ProductMaterial getProductMaterialById(ProductMaterialId id) {
        return productMaterialRepository.findById(id).orElse(null);
    }

    public ProductMaterial getProductMaterialByProductId(int productId) {
        return productMaterialRepository.findProductMaterialByProductId(productId);
    }

    public void saveProductMaterial(ProductMaterial productMaterial) {
        productMaterialRepository.save(productMaterial);
    }

    public void deleteProductMaterialById(ProductMaterialId id) {
        productMaterialRepository.deleteById(id);
    }

    public void deleteProductMaterialById(int productId, int materialId) {
        productMaterialRepository.deleteById(new ProductMaterialId(productId, materialId));
    }
}

