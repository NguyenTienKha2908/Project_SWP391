package com.example.CustomerView.service;

import com.example.CustomerView.entity.MaterialPriceList;
import com.example.CustomerView.repository.MaterialPriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialPriceListServiceImpl implements MaterialPriceListService {

    @Autowired
    private MaterialPriceListRepository materialPriceListRepository;

    @Override
    public MaterialPriceList findTopByMaterialId(int materialId) {
        return materialPriceListRepository.findTopByMaterialId(materialId);
    }
}
