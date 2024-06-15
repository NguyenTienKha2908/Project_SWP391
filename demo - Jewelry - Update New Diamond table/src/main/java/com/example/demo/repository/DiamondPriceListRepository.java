package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.DiamondPriceList;

public interface DiamondPriceListRepository extends JpaRepository<DiamondPriceList, Integer> {
    // boolean existsByGemStone_GemId(int GemId);
}
