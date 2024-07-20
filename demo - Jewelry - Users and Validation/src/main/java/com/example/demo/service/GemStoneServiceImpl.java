package com.example.demo.service;

import com.example.demo.entity.GemStone;
import com.example.demo.repository.GemStoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GemStoneServiceImpl implements GemStoneService {

    @Autowired
    private GemStoneRepository gemStoneRepository;

    @Override
    public Page<GemStone> getAllGemStones(Pageable pageable) {
        return gemStoneRepository.findAll(pageable);
    }

    @Override
    public List<GemStone> getAllActiveGemStones() {
        return gemStoneRepository.findByStatus(1);
    }

    @Override
    public GemStone getGemStoneById(int id) {
        Optional<GemStone> optional = gemStoneRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void saveGemStone(GemStone gemStone) {
        gemStoneRepository.save(gemStone);
    }
}