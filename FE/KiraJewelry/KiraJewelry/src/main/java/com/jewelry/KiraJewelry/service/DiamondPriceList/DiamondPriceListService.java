package com.jewelry.KiraJewelry.service.DiamondPriceList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.DiamondPriceList;
import com.jewelry.KiraJewelry.repository.DiamondPriceListRepository;

import java.util.Optional;

@Service
public class DiamondPriceListService {
    @Autowired
    private DiamondPriceListRepository diaPriceListRepository;

    public Page<DiamondPriceList> getAllDiaPriceLists(Pageable pageable) {
        return diaPriceListRepository.findAll(pageable);
    }

    public DiamondPriceList getDiaPriceListById(int id) {
        Optional<DiamondPriceList> optional = diaPriceListRepository.findById(id);
        return optional.orElse(null);
    }

    public void saveDiaPriceList(DiamondPriceList diaPriceList) {
        diaPriceListRepository.save(diaPriceList);
    }

    public void deleteDiaPriceListById(int id) {
        diaPriceListRepository.deleteById(id);
    }
}
