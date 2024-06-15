package com.example.CustomerView.service;

import com.example.CustomerView.entity.Material;
import com.example.CustomerView.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    @Override
    public Material getMaterialById(int id) {
        return materialRepository.findById(id).orElse(null);
    }

    @Override
    public void saveMaterial(Material material) {
        materialRepository.save(material);
    }

    @Override
    public void deleteMaterialById(int id) {
        materialRepository.deleteById(id);
    }
}