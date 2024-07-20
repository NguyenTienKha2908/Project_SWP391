package com.example.demo.service;

import com.example.demo.entity.Diamond;
import com.example.demo.repository.DiamondRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiamondServiceImpl implements DiamondService {

    @Autowired
    private DiamondRepository diamondRepository;

    @Override
    public Page<Diamond> getAllDiamonds(Pageable pageable) {
        return diamondRepository.findAll(pageable);
    }

    @Override
    public List<Diamond> getAllActiveDiamonds() {
        return diamondRepository.findByStatus(1);
    }

    @Override
    public Diamond getDiamondById(int id) {
        Optional<Diamond> optional = diamondRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void saveDiamond(Diamond diamond) {
        diamondRepository.save(diamond);
    }
}
