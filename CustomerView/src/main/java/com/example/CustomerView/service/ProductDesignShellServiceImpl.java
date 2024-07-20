package com.example.CustomerView.service;

import com.example.CustomerView.entity.ProductDesignShell;
import com.example.CustomerView.repository.ProductDesignShellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDesignShellServiceImpl implements ProductDesignShellService {

    @Autowired
    private ProductDesignShellRepository productDesignShellRepository;

    @Override
    public ProductDesignShell findByMaterialIdAndWeight(int materialId, int weight) {
        return productDesignShellRepository.findByMaterialIdAndWeight(materialId, weight);
    }
}
