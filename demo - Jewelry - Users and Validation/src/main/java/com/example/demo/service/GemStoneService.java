package com.example.demo.service;

import com.example.demo.entity.GemStone;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GemStoneService {
    Page<GemStone> getAllGemStones(Pageable pageable);
    List<GemStone> getAllActiveGemStones();
    GemStone getGemStoneById(int id);
    void saveGemStone(GemStone gemStone);
}