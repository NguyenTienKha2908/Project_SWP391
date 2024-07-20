package com.example.demo.service;

import com.example.demo.entity.DiamondPriceList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DiamondPriceListService {
    Page<DiamondPriceList> getAllDiaPriceLists(Pageable pageable);
    DiamondPriceList getDiaPriceListById(int id);
    void saveDiaPriceList(DiamondPriceList diaPriceList);
    void deleteDiaPriceListById(int id);
    List<String> getAllOrigins();
    List<Float> getCaratWeightsByOrigin(String origin);
    List<String> getColorsByOriginAndCaratWeight(String origin, float caratWeight);
    List<String> getClaritiesByOriginCaratWeightAndColor(String origin, float caratWeight, String color);
    List<String> getCutsByOriginCaratWeightColorAndClarity(String origin, float caratWeight, String color, String clarity);
    DiamondPriceList getPriceByDetails(String origin, float caratWeight, String color, String clarity, String cut);
}
