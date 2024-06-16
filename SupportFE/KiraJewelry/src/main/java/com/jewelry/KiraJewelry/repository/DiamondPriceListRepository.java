package com.jewelry.KiraJewelry.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.jewelry.KiraJewelry.models.DiamondPriceList;

public interface DiamondPriceListRepository extends JpaRepository<DiamondPriceList, Integer> {
    // boolean existsByGemStone_GemId(int GemId);
}

