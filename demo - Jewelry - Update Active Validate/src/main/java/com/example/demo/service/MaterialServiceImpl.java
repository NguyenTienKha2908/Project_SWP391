package com.example.demo.service;

import com.example.demo.entity.Material;
import com.example.demo.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

     @Override
    public List<Material> getAllActiveMaterials() {
        return materialRepository.findAll().stream()
               .filter(material -> material.getStatus() == 1)
               .collect(Collectors.toList());
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
    public String deleteMaterialById(int id) {
        Optional<Material> optionalMaterial = materialRepository.findById(id);
        if (optionalMaterial.isPresent()) {
            Material material = optionalMaterial.get();
            material.setStatus(0); // Set status to 0 for deactivation
            materialRepository.save(material);
            return "Material status updated successfully.";
        }
        return "Material not found.";
    }

    @Override
    public boolean canDeleteMaterial(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canDeleteMaterial'");
    }
}
