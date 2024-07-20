package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.MaterialPriceList;

public interface MaterialPriceListRepository extends JpaRepository<MaterialPriceList, Integer> {
    boolean existsByMaterial_MaterialId(int materialId);
}
