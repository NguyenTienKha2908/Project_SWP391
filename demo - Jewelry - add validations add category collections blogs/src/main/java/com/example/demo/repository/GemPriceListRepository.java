package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.GemPriceList;

public interface GemPriceListRepository extends JpaRepository<GemPriceList, Integer> {
    // boolean existsByGemStone_GemId(int GemId);
}
