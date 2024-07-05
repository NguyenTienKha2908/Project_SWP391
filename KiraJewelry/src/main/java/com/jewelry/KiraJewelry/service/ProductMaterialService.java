package com.jewelry.KiraJewelry.service;

import java.util.List;

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

    public void deleteProductMaterial(ProductMaterial productMaterial) {
        productMaterialRepository.delete(productMaterial);
    }

    public void saveProductMaterial(ProductMaterial productMaterial) {
        productMaterialRepository.save(productMaterial);
    }

    public ProductMaterial getProductMaterialByProductId(int productId) {
        return productMaterialRepository.findProductMaterialByProductId(productId);
    }

    public List<ProductMaterial> getListProductMaterialByProductId(int productId) {
        return productMaterialRepository.findListProductMaterialByProductId(productId);
    }

    public ProductMaterial getProductMaterialByProduct_id(int product_id) {
        return productMaterialRepository.findByProduct_Id(product_id);
    }

    public ProductMaterial findByProductIdAndMaterialId(int productId, int materialId) {
        return productMaterialRepository.findByIdProductIdAndIdMaterialId(productId, materialId);
    }

    public ProductMaterial findByProductId(int productId) {
        return productMaterialRepository.findByProduct_Id(productId);
    }
}
