package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.GemStone;
@Repository
public interface GemStoneRepository extends JpaRepository<GemStone, Integer> {
    
}
