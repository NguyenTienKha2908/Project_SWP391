package com.example.demo.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.demo.entity.GemPriceList;


public interface GemPriceListService {
    Page<GemPriceList> getAllGemPriceLists(Pageable pageable);
    GemPriceList getGemPriceListById(int id);
    void saveGemPriceList(GemPriceList gemPriceList);
    void deleteGemPriceListById(int id);
}
