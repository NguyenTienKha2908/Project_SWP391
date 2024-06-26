package com.jewelry.KiraJewelry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.Diamond;
import java.util.List;
import com.jewelry.KiraJewelry.repository.DiamondRepository;

@Service
public class DiamondService {
    @Autowired
    private DiamondRepository diamondRepository;

    public List<Diamond> getAllDiamonds() {
        return diamondRepository.findAll();
    }

    public void saveDiamond(Diamond diamond) {
        diamondRepository.save(diamond);
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
