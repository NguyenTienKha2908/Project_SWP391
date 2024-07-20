package com.example.demo.service;

import com.example.demo.entity.MaterialPriceList;
import java.util.List;

public interface MaterialPriceListService {
    List<MaterialPriceList> getAllMaterialPriceLists();
    MaterialPriceList getMaterialPriceListById(int id);
    void saveMaterialPriceList(MaterialPriceList materialPriceList);
    void deleteMaterialPriceListById(int id);
}
