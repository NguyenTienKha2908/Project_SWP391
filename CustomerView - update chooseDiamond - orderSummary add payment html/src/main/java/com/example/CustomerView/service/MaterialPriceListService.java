package com.example.CustomerView.service;

import com.example.CustomerView.entity.MaterialPriceList;

public interface MaterialPriceListService {
    MaterialPriceList findTopByMaterialId(int materialId);
}
