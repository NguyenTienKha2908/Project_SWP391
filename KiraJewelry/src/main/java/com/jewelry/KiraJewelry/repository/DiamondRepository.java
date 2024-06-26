package com.jewelry.KiraJewelry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.Diamond;

@Repository
public interface DiamondRepository extends JpaRepository<Diamond, Integer>, JpaSpecificationExecutor<Diamond> {

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
