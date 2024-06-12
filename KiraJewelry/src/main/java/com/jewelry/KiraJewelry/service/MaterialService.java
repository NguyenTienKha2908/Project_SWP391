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

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }
}
