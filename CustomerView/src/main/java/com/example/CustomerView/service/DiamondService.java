package com.example.CustomerView.service;

import com.example.CustomerView.entity.Diamond;

import java.util.List;

public interface DiamondService {
    List<Diamond> findAvailableDiamondsByWeightRange(float minWeight, float maxWeight);
    Diamond getDiamondById(int diamondId);
    void saveDiamond(Diamond diamond);
    float findMinWeight();
    float findMaxWeight();
}
