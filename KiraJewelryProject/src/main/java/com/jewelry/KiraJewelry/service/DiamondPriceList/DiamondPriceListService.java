package com.jewelry.KiraJewelry.service.DiamondPriceList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.DiamondPriceList;
import com.jewelry.KiraJewelry.repository.DiamondPriceListRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<String> getAllOrigins() {
        return diaPriceListRepository.findAll()
                .stream()
                .map(DiamondPriceList::getOrigin)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Double> getCaratWeightsByOrigin(String origin) {
        return diaPriceListRepository.findByOrigin(origin)
                .stream()
                .map(DiamondPriceList::getCarat_Weight_From)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getColorsByOriginAndCaratWeight(String origin, double caratWeight) {
        return diaPriceListRepository.findDistinctColorsByOriginAndCaratWeight(origin, caratWeight);
    }

    public List<String> getClaritiesByOriginCaratWeightAndColor(String origin, double caratWeight, String color) {
        return diaPriceListRepository.findDistinctClaritiesByOriginCaratWeightAndColor(origin, caratWeight, color);
    }

    public List<String> getCutsByOriginCaratWeightColorAndClarity(String origin, double caratWeight, String color,
            String clarity) {
        return diaPriceListRepository.findDistinctCutsByOriginCaratWeightColorAndClarity(origin, caratWeight, color,
                clarity);
    }

    public DiamondPriceList getPriceByDetails(String origin, double caratWeight, String color, String clarity,
            String cut) {
        DiamondPriceList priceList = diaPriceListRepository.findByOriginCaratWeightColorClarityAndCut(origin,
                caratWeight, color, clarity, cut);
        if (priceList != null) {
            System.out.println("Fetched Price: " + priceList.getPrice()); // Log the fetched price
        } else {
            System.out.println("No matching price found for the provided details."); // Log no match found
        }
        return priceList;
    }

    public List<DiamondPriceList> findPriceListByCriteria(double caratWeight, String color, String clarity,
            String cut, String origin) {
        return diaPriceListRepository.findPriceListByCriteria(caratWeight, color, clarity, cut, origin);
    }
}
