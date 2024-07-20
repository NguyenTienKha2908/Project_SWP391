package com.example.CustomerView.repository;

import com.example.CustomerView.entity.Diamond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiamondRepository extends JpaRepository<Diamond, Integer> {
    @Query("SELECT MIN(d.carat_Weight) FROM Diamond d WHERE d.status = true")
    float findMinWeight();

    @Query("SELECT MAX(d.carat_Weight) FROM Diamond d WHERE d.status = true")
    float findMaxWeight();

    @Query("SELECT d FROM Diamond d WHERE d.status = true AND d.carat_Weight BETWEEN :minWeight AND :maxWeight")
    List<Diamond> findAvailableDiamondsByWeightRange(@Param("minWeight") double minWeight, @Param("maxWeight") double maxWeight);

    @Query("SELECT d FROM Diamond d WHERE d.product_Id = :productId")
    Diamond findByProductId(int productId);
}
