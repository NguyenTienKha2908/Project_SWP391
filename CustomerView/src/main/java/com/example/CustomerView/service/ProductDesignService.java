package com.example.CustomerView.service;

import com.example.CustomerView.entity.ProductDesign;
import java.util.List;

public interface ProductDesignService {
    List<ProductDesign> getAllProductDesigns();
    ProductDesign getProductDesignById(int id);
    void saveProductDesign(ProductDesign productDesign);
    void deleteProductDesignById(int id);
    int findMaxProductDesignId();
    String findMaxProductDesignCode();
    int generateNewProductDesignId();
    String generateNewProductDesignCode();
}
