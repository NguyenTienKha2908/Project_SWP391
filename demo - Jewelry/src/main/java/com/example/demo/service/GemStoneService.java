package com.example.demo.service;

import com.example.demo.entity.GemStone;
import java.util.List;

public interface GemStoneService {
    List<GemStone> getAllGemStones();
    List<GemStone> getAllActiveGemStones();
    GemStone getGemStoneById(int id);
    void saveGemStone(GemStone gemStone);
}
