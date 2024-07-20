package com.example.demo.service;

import com.example.demo.entity.Diamond;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiamondService {
    Page<Diamond> getAllDiamonds(Pageable pageable);
    List<Diamond> getAllActiveDiamonds();
    Diamond getDiamondById(int id);
    void saveDiamond(Diamond diamond);
}