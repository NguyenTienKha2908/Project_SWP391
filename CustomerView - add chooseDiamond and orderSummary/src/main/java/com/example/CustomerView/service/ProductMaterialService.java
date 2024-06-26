package com.example.CustomerView.service;

import com.example.CustomerView.entity.ProductMaterial;
import java.util.List;

public interface ProductMaterialService {
    List<ProductMaterial> getAllProductMaterials();
    ProductMaterial getProductMaterialById(int id);
    ProductMaterial getProductMaterialByProduct_id(int product_id);
    void saveProductMaterial(ProductMaterial productMaterial);
    void deleteProductMaterialById(int id);
}
