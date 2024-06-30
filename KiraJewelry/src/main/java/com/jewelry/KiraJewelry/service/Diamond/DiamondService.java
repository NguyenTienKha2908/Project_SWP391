package com.jewelry.KiraJewelry.service.Diamond;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.Diamond;
import com.jewelry.KiraJewelry.repository.DiamondRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DiamondService {
    @Autowired
    private DiamondRepository diamondRepository;

    public List<Diamond> getAllDiamonds() {
        return diamondRepository.findAll();
    }
    
    public Page<Diamond> getAllDiamonds(Pageable pageable) {
        return diamondRepository.findAll(pageable);
    }

    public List<Diamond> getAllActiveDiamonds() {
        return diamondRepository.findByStatus(true);
    }

    public Diamond getDiamondById(int id) {
        Optional<Diamond> optional = diamondRepository.findById(id);
        return optional.orElse(null);
    }

    public void saveDiamond(Diamond diamond) {
        diamondRepository.save(diamond);
    }

    public float findMinWeight() {
        return diamondRepository.findMinWeight();
    }

    public float findMaxWeight() {
        return diamondRepository.findMaxWeight();
    }

    public List<Diamond> findAvailableDiamondsByWeightRange(float minWeight, float maxWeight) {
        return diamondRepository.findAvailableDiamondsByWeightRange(minWeight, maxWeight);
    }

    public Diamond getDiamondByProductId(int product_Id) {
        return diamondRepository.getDiamondByProductId(product_Id);
    }
}
