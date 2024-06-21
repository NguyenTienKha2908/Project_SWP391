package com.jewelry.KiraJewelry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.Diamond;
import java.util.List;
import com.jewelry.KiraJewelry.repository.DiamondRepository;
import com.jewelry.KiraJewelry.specification.DiamondSpecification;

@Service
public class DiamondService {
    @Autowired
    private DiamondRepository diamondRepository;

    public List<Diamond> getAllDiamonds() {
        return diamondRepository.findAll();
    }

    public Diamond getDiamondByProductId(int productId) {
        return diamondRepository.findDiamondByProductId(productId);
    }

    public Diamond getDiamondById(int dia_Id) {
        return diamondRepository.findDiamondById(dia_Id);
    }

    public void saveDiamond(Diamond diamond) {
        diamondRepository.save(diamond);
    }

    public List<Diamond> findByName(String name) {
        return diamondRepository.findByNameContaining(name);
    }

    public List<Diamond> findByCriteria(String name, Double caratWeight, String color, String clarity, String cut,
            String origin) {
        Specification<Diamond> spec = DiamondSpecification.findByCriteria(name, caratWeight, color, clarity, cut,
                origin);
        return diamondRepository.findAll(spec);
    }

}
