package com.example.demo.service;

import com.example.demo.entity.DiamondPriceList;
import com.example.demo.repository.DiamondPriceListRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiamondPriceListServiceImpl implements DiamondPriceListService {

    @Autowired
    private DiamondPriceListRepository diaPriceListRepository;

    @Override
    public Page<DiamondPriceList> getAllDiaPriceLists(Pageable pageable) {
        return diaPriceListRepository.findAll(pageable);
    }

    @Override
    public DiamondPriceList getDiaPriceListById(int id) {
        Optional<DiamondPriceList> optional = diaPriceListRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void saveDiaPriceList(DiamondPriceList diaPriceList) {
        diaPriceListRepository.save(diaPriceList);
    }

    @Override
    public void deleteDiaPriceListById(int id) {
        diaPriceListRepository.deleteById(id);
    }

    @Override
    public List<String> getAllOrigins() {
        return diaPriceListRepository.findAll()
                .stream()
                .map(DiamondPriceList::getOrigin)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Float> getCaratWeightsByOrigin(String origin) {
        return diaPriceListRepository.findByOrigin(origin)
                .stream()
                .map(DiamondPriceList::getCarat_weight_from)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getColorsByOriginAndCaratWeight(String origin, float caratWeight) {
        return diaPriceListRepository.findDistinctColorsByOriginAndCaratWeight(origin, caratWeight);
    }

    @Override
    public List<String> getClaritiesByOriginCaratWeightAndColor(String origin, float caratWeight, String color) {
        return diaPriceListRepository.findDistinctClaritiesByOriginCaratWeightAndColor(origin, caratWeight, color);
    }

    @Override
    public List<String> getCutsByOriginCaratWeightColorAndClarity(String origin, float caratWeight, String color, String clarity) {
        return diaPriceListRepository.findDistinctCutsByOriginCaratWeightColorAndClarity(origin, caratWeight, color, clarity);
    }

    @Override
    public DiamondPriceList getPriceByDetails(String origin, float caratWeight, String color, String clarity, String cut) {
        return diaPriceListRepository.findByOriginCaratWeightColorClarityAndCut(origin, caratWeight, color, clarity, cut);
    }
}
