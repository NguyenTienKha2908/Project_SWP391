package com.jewelry.KiraJewelry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.MaterialPriceList;
import com.jewelry.KiraJewelry.repository.MaterialPriceListRepository;

@Service
public class MaterialPriceListService {
    @Autowired
    public MaterialPriceListRepository materialPriceListRepository;

    public List<MaterialPriceList> getAllPriceLists() {
        return materialPriceListRepository.findAll();
    }
}
