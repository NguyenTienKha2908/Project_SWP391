package com.example.demo.service;

import com.example.demo.entity.DiamondPriceList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiamondPriceListService {
    Page<DiamondPriceList> getAllDiaPriceLists(Pageable pageable);
    DiamondPriceList getDiaPriceListById(int id);
    void saveDiaPriceList(DiamondPriceList diaPriceList);
    void deleteDiaPriceListById(int id);
}
