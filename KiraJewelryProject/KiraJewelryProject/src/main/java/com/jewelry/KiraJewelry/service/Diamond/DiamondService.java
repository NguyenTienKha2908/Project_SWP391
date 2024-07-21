package com.jewelry.KiraJewelry.service.Diamond;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.Diamond;
import com.jewelry.KiraJewelry.repository.DiamondRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DiamondService {
    @Autowired
    private DiamondRepository diamondRepository;

    public List<Diamond> getAllDiamonds() {
        return diamondRepository.findAll();
    }

    public Page<Diamond> getAllDiamonds(Pageable pageable) {
        return diamondRepository.findAll(pageable);
    }

    public List<Diamond> getAllActiveDiamonds() {
        return diamondRepository.findByStatus(true);
    }

    public Diamond getDiamondById(int id) {
        Optional<Diamond> optional = diamondRepository.findById(id);
        return optional.orElse(null);
    }

    public List<Diamond> getListDiamondByProductId(int product_Id) {
        List<Diamond> diamondList = diamondRepository.findByProductId(product_Id);
        return diamondList;
    }

    public void saveDiamond(Diamond diamond) {
        diamondRepository.save(diamond);
    }

    public float findMinWeight() {
        return diamondRepository.findMinWeight();
    }

    public float findMaxWeight() {
        return diamondRepository.findMaxWeight();
    }

    public List<Diamond> findAvailableDiamondsByWeightRange(double minWeight, double maxWeight) {
        return diamondRepository.findAvailableDiamondsByWeightRange(minWeight, maxWeight);
    }

    public Diamond getDiamondByProductId(int product_Id) {
        return diamondRepository.getDiamondByProductId(product_Id);
    }

    public List<Diamond> getByListDiamonds(String name, Double caratWeight, String color, String clarity, String cut,
            String origin) {
        System.out.println("List Diamonds - diamondName: " + name + ", caratWeight: " + caratWeight + ", color: "
                + color + ", clarity: " + clarity + ", cut: " + cut + ", origin: " + origin);

        return diamondRepository.findByListDiamonds(name, caratWeight, color, clarity, cut, origin);
    }

    public List<Diamond> getByListDiamondsLackWeight(String name, String color, String clarity, String cut,
            String origin) {
        System.out.println("List Diamonds - diamondName: " + name
                + ", color: " + color + ", clarity: " + clarity + ", cut: " + cut + ", origin: " + origin);

        return diamondRepository.findByListDiamondsLackWeight(name, color, clarity, cut, origin);
    }
}
