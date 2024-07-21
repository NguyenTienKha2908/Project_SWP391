package com.jewelry.KiraJewelry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jewelry.KiraJewelry.models.ProductDesign;
import com.jewelry.KiraJewelry.repository.ProductDesignRepository;

import java.util.List;

@Service
public class ProductDesignService {

    @Autowired
    private ProductDesignRepository productDesignRepository;

    public List<ProductDesign> getAllProductDesigns() {
        return productDesignRepository.findAll();
    }

    public ProductDesign getProductDesignById(int id) {
        return productDesignRepository.findById(id).orElse(null);
    }

    @Transactional
    public void saveProductDesign(ProductDesign productDesign) {
        productDesignRepository.save(productDesign);
    }

    public void deleteProductDesignById(int id) {
        productDesignRepository.deleteById(id);
    }

    public int findMaxProductDesignId() {
        Integer maxId = productDesignRepository.findMaxProductDesignId();
        return maxId != null ? maxId : 0;
    }

    public String findMaxProductDesignCode() {
        return productDesignRepository.findMaxProductDesignCode();
    }

    public int generateNewProductDesignId() {
        return findMaxProductDesignId() + 1;
    }

    public String generateNewProductDesignCode() {
        String maxProductDesignCode = findMaxProductDesignCode();
        if (maxProductDesignCode == null) {
            return "PD00001"; // Provide a default value if maxProductDesignCode is null
        }
        int numericPart = Integer.parseInt(maxProductDesignCode.substring(2));
        int newNumericPart = numericPart + 1;
        return "PD" + String.format("%05d", newNumericPart);
    }

    public ProductDesign findByProductId(int productId) {
        return productDesignRepository.findByProductId(productId);
    }
}

