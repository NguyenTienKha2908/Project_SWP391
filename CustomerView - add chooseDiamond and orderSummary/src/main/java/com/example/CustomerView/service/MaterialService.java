package com.example.CustomerView.service;

import com.example.CustomerView.entity.Material;
import java.util.List;

public interface MaterialService {
    List<Material> getAllMaterials();
    Material getMaterialById(int id);
    void saveMaterial(Material material);
    void deleteMaterialById(int id);
    List<Material> getMaterialsForProductDesignShell(int productId);
}
