package com.example.demo.service;

import com.example.demo.entity.MaterialPriceList;
import com.example.demo.repository.MaterialPriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialPriceListServiceImpl implements MaterialPriceListService {

    @Autowired
    private MaterialPriceListRepository materialPriceListRepository;

    @Override
    public List<MaterialPriceList> getAllMaterialPriceLists() {
        return materialPriceListRepository.findAll();
    }

    @Override
    public MaterialPriceList getMaterialPriceListById(int id) {
        Optional<MaterialPriceList> optional = materialPriceListRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void saveMaterialPriceList(MaterialPriceList materialPriceList) {
        materialPriceListRepository.save(materialPriceList);
    }

    @Override
    public void deleteMaterialPriceListById(int id) {
        materialPriceListRepository.deleteById(id);
    }
}
