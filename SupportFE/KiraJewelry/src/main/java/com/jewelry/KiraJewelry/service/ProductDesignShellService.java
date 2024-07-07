package com.jewelry.KiraJewelry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.ProductDesignShell;
import com.jewelry.KiraJewelry.repository.ProductDesignShellRepository;

@Service
public class ProductDesignShellService {

    @Autowired
    private ProductDesignShellRepository productDesignShellRepository;

    public ProductDesignShell findByMaterialIdAndWeight(int materialId, int weight) {
        return productDesignShellRepository.findByMaterialIdAndWeight(materialId, weight);
    }
}

