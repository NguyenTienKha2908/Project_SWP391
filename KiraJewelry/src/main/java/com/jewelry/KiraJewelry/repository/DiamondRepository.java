package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.models.Diamond;

import java.util.List;

@Repository
public interface DiamondRepository extends JpaRepository<Diamond, Integer> {
    List<Diamond> findByStatus(boolean status);

    @Query("SELECT MIN(d.carat_Weight) FROM Diamond d WHERE d.status = true")
    float findMinWeight();

    @Query("SELECT MAX(d.carat_Weight) FROM Diamond d WHERE d.status = true")
    float findMaxWeight();

    @Query("SELECT d FROM Diamond d WHERE d.product.product_Id = :product_Id")
    Diamond getDiamondByProductId(@Param("product_Id") int product_Id);

    @Query("SELECT d FROM Diamond d WHERE d.status = true AND d.carat_Weight BETWEEN :minWeight AND :maxWeight")
    List<Diamond> findAvailableDiamondsByWeightRange(@Param("minWeight") float minWeight, @Param("maxWeight") float maxWeight);
}
