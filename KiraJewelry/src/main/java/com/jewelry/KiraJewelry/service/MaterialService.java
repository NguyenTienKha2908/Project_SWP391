package com.jewelry.KiraJewelry.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.Material;
import com.jewelry.KiraJewelry.repository.MaterialRepository;

@Service
public class MaterialService {
    @Autowired
    public MaterialRepository materialRepository;

    public Material getMaterialByName(String material_Name) {
        return materialRepository.findMaterialByName(material_Name);
    }

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    public List<Material> getAllActiveMaterials() {
        return materialRepository.findAll().stream()
                .filter(material -> material.getStatus() == 1)
                .collect(Collectors.toList());
    }

    public Material getMaterialById(int id) {
        Optional<Material> optional = materialRepository.findById(id);
        return optional.orElse(null);
    }

    public void saveMaterial(Material material) {
        materialRepository.save(material);
    }

    public void deleteMaterialById(int id) {
        materialRepository.deleteById(id);
    }

    public List<Material> getMaterialsForProductDesignShell(int productId) {
        return materialRepository.findMaterialsForProductDesignShell(productId);
    }

    public List<Material> findByName(String name) {
        return materialRepository.findByNameContaining(name);
    }
}
