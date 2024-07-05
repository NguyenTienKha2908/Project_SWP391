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
        List<Diamond> findAvailableDiamondsByWeightRange(@Param("minWeight") double minWeight,
                        @Param("maxWeight") double maxWeight);

        @Query("SELECT d FROM Diamond d WHERE d.dia_Name LIKE %:name% AND d.carat_Weight = :caratWeight AND d.color = :color AND d.clarity = :clarity AND d.cut = :cut AND d.origin = :origin")
        List<Diamond> findByListDiamonds(@Param("name") String name, @Param("caratWeight") Double caratWeight,
                        @Param("color") String color,
                        @Param("clarity") String clarity,
                        @Param("cut") String cut, @Param("origin") String origin);

        @Query("SELECT d FROM Diamond d WHERE d.dia_Name LIKE %:name% AND (:color IS NULL OR d.color = :color) AND (:clarity IS NULL OR d.clarity = :clarity) AND (:cut IS NULL OR d.cut = :cut) AND (:origin IS NULL OR d.origin = :origin)")
        List<Diamond> findByListDiamondsLackWeight(@Param("name") String name,
                        @Param("color") String color,
                        @Param("clarity") String clarity,
                        @Param("cut") String cut, @Param("origin") String origin);

        @Query("SELECT d FROM Diamond d WHERE d.product.product_Id = :product_Id")
        List<Diamond> findByProductId(@Param("product_Id") int product_Id);
}
