package com.example.demo.service;

import com.example.demo.entity.Material;
import com.example.demo.repository.MaterialRepository;
import com.example.demo.repository.MaterialPriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialPriceListRepository materialPriceListRepository;

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
    public String deleteMaterialById(int id) {
        if (!canDeleteMaterial(id)) {
            return "Cannot delete material as it has associated material price lists.";
        }
        materialRepository.deleteById(id);
        return "Material deleted successfully.";
    }

    @Override
    public boolean canDeleteMaterial(int id) {
        return !materialPriceListRepository.existsByMaterial_MaterialId(id);
    }
}
