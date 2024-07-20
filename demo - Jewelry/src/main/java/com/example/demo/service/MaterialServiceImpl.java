package com.example.demo.service;

import com.example.demo.entity.Material;
import com.example.demo.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Material> optional = materialRepository.findById(id);
        return optional.orElse(null);
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
