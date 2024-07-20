package com.example.CustomerView.service;

import com.example.CustomerView.entity.Diamond;

import java.util.List;

public interface DiamondService {
    List<Diamond> findAvailableDiamondsByWeightRange(double minWeight, double maxWeight);
    Diamond getDiamondById(int diamondId);
    Diamond getDiamondByProductId(int productId); 
    void saveDiamond(Diamond diamond);
    double findMinWeight();
    double findMaxWeight();
}