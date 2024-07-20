package com.example.CustomerView.service;

import com.example.CustomerView.entity.ProductDesign;
import com.example.CustomerView.repository.ProductDesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductDesignServiceImpl implements ProductDesignService {

    @Autowired
    private ProductDesignRepository productDesignRepository;

    @Override
    public List<ProductDesign> getAllProductDesigns() {
        return productDesignRepository.findAll();
    }

    @Override
    public ProductDesign getProductDesignById(int id) {
        return productDesignRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveProductDesign(ProductDesign productDesign) {
        productDesignRepository.save(productDesign);
    }

    @Override
    public void deleteProductDesignById(int id) {
        productDesignRepository.deleteById(id);
    }

    @Override
    public int findMaxProductDesignId() {
        Integer maxId = productDesignRepository.findMaxProductDesignId();
        return maxId != null ? maxId : 0;
    }

    @Override
    public String findMaxProductDesignCode() {
        return productDesignRepository.findMaxProductDesignCode();
    }

    @Override
    public int generateNewProductDesignId() {
        return findMaxProductDesignId() + 1;
    }

    @Override
    public String generateNewProductDesignCode() {
        String maxProductDesignCode = findMaxProductDesignCode();
        if (maxProductDesignCode == null) {
            return "PD00001"; // Provide a default value if maxProductDesignCode is null
        }
        int numericPart = Integer.parseInt(maxProductDesignCode.substring(2));
        int newNumericPart = numericPart + 1;
        return "PD" + String.format("%05d", newNumericPart);
    }

    @Override
    public ProductDesign findByProductId(int productId) {
        return productDesignRepository.findByProductId(productId);
    }
}
