package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jewelry.KiraJewelry.models.Diamond;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiamondRepository extends JpaRepository<Diamond, Integer> {
    List<Diamond> findByStatus(boolean status);

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
}
