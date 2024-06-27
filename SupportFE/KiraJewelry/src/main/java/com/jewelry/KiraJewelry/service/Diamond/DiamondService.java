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

    public void saveDiamond(Diamond diamond) {
        diamondRepository.save(diamond);
    }

    public List<Diamond> getAllDiamonds() {
        return diamondRepository.findAll();
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
