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
        return diamondRepository.findByStatus(1);
    }

    public Diamond getDiamondById(int id) {
        Optional<Diamond> optional = diamondRepository.findById(id);
        return optional.orElse(null);
    }

    public void saveDiamond(Diamond diamond) {
        diamondRepository.save(diamond);
    }
}
