package com.jewelry.KiraJewelry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.Diamond_Price_List;

@Repository
public interface DiamondPriceListRepository extends JpaRepository<Diamond_Price_List, Integer> {
        @Query("SELECT d FROM Diamond_Price_List d WHERE d.color = :color AND d.clarity = :clarity AND d.cut = :cut AND d.origin = :origin AND :caratWeight BETWEEN d.carat_Weight_From AND d.carat_Weight_To")

        Diamond_Price_List findDiamondPrice(@Param("color") String color,
                        @Param("clarity") String clarity,
                        @Param("cut") String cut,
                        @Param("origin") String origin,
                        @Param("caratWeight") double caratWeight);

        @Query("SELECT d FROM Diamond_Price_List d WHERE d.color = :color AND d.clarity = :clarity AND d.cut = :cut AND d.origin = :origin")

        Diamond_Price_List findDiamondPriceList(@Param("color") String color,
                        @Param("clarity") String clarity,
                        @Param("cut") String cut,
                        @Param("origin") String origin);

        @Query("SELECT dpl FROM Diamond_Price_List dpl WHERE dpl.carat_Weight_From <= :caratWeight AND dpl.carat_Weight_To >= :caratWeight AND dpl.color = :color AND dpl.clarity = :clarity AND dpl.cut = :cut AND dpl.origin = :origin")
        List<Diamond_Price_List> findPriceListByCriteria(@Param("caratWeight") double caratWeight,
                        @Param("color") String color,
                        @Param("clarity") String clarity,
                        @Param("cut") String cut, @Param("origin") String origin);
}
