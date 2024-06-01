package com.example.demo.service;

import com.example.demo.entity.Material;
import java.util.List;

public interface MaterialService {
    List<Material> getAllMaterials();
    Material getMaterialById(int id);
    void saveMaterial(Material material);
    String deleteMaterialById(int id);
    boolean canDeleteMaterial(int id);
    List<Material> getAllActiveMaterials(); 
}
