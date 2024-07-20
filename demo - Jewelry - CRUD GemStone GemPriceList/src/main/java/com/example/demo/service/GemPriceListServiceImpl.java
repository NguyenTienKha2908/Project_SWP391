package com.example.demo.service;

import com.example.demo.entity.GemPriceList;
import com.example.demo.repository.GemPriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GemPriceListServiceImpl implements GemPriceListService {

    @Autowired
    private GemPriceListRepository gemPriceListRepository;

    @Override
    public List<GemPriceList> getAllGemPriceLists() {
        return gemPriceListRepository.findAll();
    }

    @Override
    public GemPriceList getGemPriceListById(int id) {
        Optional<GemPriceList> optional = gemPriceListRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void saveGemPriceList(GemPriceList gemPriceList) {
        gemPriceListRepository.save(gemPriceList);
    }

    @Override
    public void deleteGemPriceListById(int id) {
        gemPriceListRepository.deleteById(id);
    }
}
