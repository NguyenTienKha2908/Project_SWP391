package com.example.CustomerView.service;

import com.example.CustomerView.entity.Diamond;
import com.example.CustomerView.repository.DiamondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiamondServiceImpl implements DiamondService {

    @Autowired
    private DiamondRepository diamondRepository;

    @Override
    public List<Diamond> findAvailableDiamondsByWeightRange(float minWeight, float maxWeight) {
        return diamondRepository.findAvailableDiamondsByWeightRange(minWeight, maxWeight);
    }

    @Override
    public Diamond getDiamondById(int diamondId) {
        return diamondRepository.findById(diamondId).orElse(null);
    }

    @Override
    public void saveDiamond(Diamond diamond) {
        diamondRepository.save(diamond);
    }

    @Override
    public float findMinWeight() {
        return diamondRepository.findMinWeight();
    }

    @Override
    public float findMaxWeight() {
        return diamondRepository.findMaxWeight();
    }
}
