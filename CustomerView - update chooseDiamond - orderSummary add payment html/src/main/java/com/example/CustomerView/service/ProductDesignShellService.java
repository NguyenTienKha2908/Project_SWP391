package com.example.CustomerView.service;

import com.example.CustomerView.entity.ProductDesignShell;

public interface ProductDesignShellService {
    ProductDesignShell findByMaterialIdAndWeight(int materialId, int weight);
}
