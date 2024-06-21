package com.jewelry.KiraJewelry.service;

import java.util.List;

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

    public Material getMaterialById(int material_Id) {
        return materialRepository.findMaterialById(material_Id);
    }

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    public List<Material> findByName(String name) {
        return materialRepository.findByNameContaining(name);
    }
}
