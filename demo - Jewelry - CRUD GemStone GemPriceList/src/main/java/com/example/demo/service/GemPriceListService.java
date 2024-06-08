package com.example.demo.service;

import com.example.demo.entity.GemPriceList;
import java.util.List;

public interface GemPriceListService {
    List<GemPriceList> getAllGemPriceLists();
    GemPriceList getGemPriceListById(int id);
    void saveGemPriceList(GemPriceList gemPriceList);
    void deleteGemPriceListById(int id);
}
