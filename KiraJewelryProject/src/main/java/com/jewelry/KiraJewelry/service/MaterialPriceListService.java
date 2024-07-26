package com.jewelry.KiraJewelry.service;

import java.util.List;
import java.util.Optional;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public MaterialPriceList getMaterialPriceListById(int id) {
        Optional<MaterialPriceList> optional = materialPriceListRepository.findById(id);
        return optional.orElse(null);
    }

    public void saveMaterialPriceList(MaterialPriceList materialPriceList) {
        materialPriceListRepository.save(materialPriceList);
    }

    public void deleteMaterialPriceListById(int id) {
        materialPriceListRepository.deleteById(id);
    }

    public MaterialPriceList getMaterialPriceListByMaterialId(int material_Id) {
        return materialPriceListRepository.findMaterialPriceListByMaterialId(material_Id);
    }

    public MaterialPriceList findTopByMaterialId(int materialId) {
        return materialPriceListRepository.findTopByMaterialId(materialId);
    }

    public MaterialPriceList getLatestPriceByMaterialId(int materialId) {
        List<MaterialPriceList> priceList = materialPriceListRepository.findAllByMaterialId(materialId);
        return priceList.stream()
                .max(Comparator.comparing(MaterialPriceList::getEff_Date))
                .orElse(null);
    }

    public Page<MaterialPriceList> getMaterialPriceListPaginated(int page, int size) {
        return materialPriceListRepository.findAll(PageRequest.of(page, size));
    }


}
