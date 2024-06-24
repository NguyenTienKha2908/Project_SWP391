package com.jewelry.KiraJewelry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.Diamond_Price_List;
import com.jewelry.KiraJewelry.repository.DiamondPriceListRepository;

@Service
public class DiamondPriceListService {
    @Autowired
    private DiamondPriceListRepository diamondPriceListRepository;

    public Diamond_Price_List getDiamondPrice(String color, String clarity, String cut, String origin,
            double carat_Weight) {
        return diamondPriceListRepository.findDiamondPrice(color, clarity, cut, origin, carat_Weight);
    }

    public Diamond_Price_List getDiamondPriceList(String color, String clarity, String cut, String origin) {
        return diamondPriceListRepository.findDiamondPriceList(color, clarity, cut, origin);
    }

    public List<Diamond_Price_List> findPriceListByCriteria(double caratWeight, String color, String clarity,
            String cut, String origin) {
        return diamondPriceListRepository.findPriceListByCriteria(caratWeight, color, clarity, cut, origin);
    }
}
