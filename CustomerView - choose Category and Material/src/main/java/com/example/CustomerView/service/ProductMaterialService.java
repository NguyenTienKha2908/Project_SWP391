package com.example.CustomerView.service;

import com.example.CustomerView.entity.ProductMaterial;
import java.util.List;

public interface ProductMaterialService {
    List<ProductMaterial> getAllProductMaterials();
    ProductMaterial getProductMaterialById(int id);
    void saveProductMaterial(ProductMaterial productMaterial);
    void deleteProductMaterialById(int id);
}
