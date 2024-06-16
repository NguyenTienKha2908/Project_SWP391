package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jewelry.KiraJewelry.models.Diamond;

import java.util.List;

public interface DiamondRepository extends JpaRepository<Diamond, Integer> {
    List<Diamond> findByStatus(int status);
}
