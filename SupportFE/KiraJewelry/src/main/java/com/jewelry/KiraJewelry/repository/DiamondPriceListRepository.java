package com.jewelry.KiraJewelry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jewelry.KiraJewelry.models.DiamondPriceList;

public interface DiamondPriceListRepository extends JpaRepository<DiamondPriceList, Integer> {
        @Query("SELECT d FROM DiamondPriceList d WHERE d.origin = :origin")
        List<DiamondPriceList> findByOrigin(@Param("origin") String origin);

        @Query("SELECT DISTINCT d.color FROM DiamondPriceList d WHERE d.origin = :origin AND :carat_Weight BETWEEN d.carat_Weight_From AND d.carat_Weight_To")
        List<String> findDistinctColorsByOriginAndCaratWeight(@Param("origin") String origin,
                        @Param("carat_Weight") double carat_Weight);

        @Query("SELECT DISTINCT d.clarity FROM DiamondPriceList d WHERE d.origin = :origin AND :carat_Weight BETWEEN d.carat_Weight_From AND d.carat_Weight_To AND d.color = :color")
        List<String> findDistinctClaritiesByOriginCaratWeightAndColor(@Param("origin") String origin,
                        @Param("carat_Weight") double carat_Weight, @Param("color") String color);

        @Query("SELECT DISTINCT d.cut FROM DiamondPriceList d WHERE d.origin = :origin AND :carat_Weight BETWEEN d.carat_Weight_From AND d.carat_Weight_To AND d.color = :color AND d.clarity = :clarity")
        List<String> findDistinctCutsByOriginCaratWeightColorAndClarity(@Param("origin") String origin,
                        @Param("carat_Weight") double carat_Weight, @Param("color") String color,
                        @Param("clarity") String clarity);

        @Query("SELECT d FROM DiamondPriceList d WHERE d.origin = :origin AND :carat_Weight BETWEEN d.carat_Weight_From AND d.carat_Weight_To AND d.color = :color AND d.clarity = :clarity AND d.cut = :cut")
        DiamondPriceList findByOriginCaratWeightColorClarityAndCut(
                        @Param("origin") String origin,
                        @Param("carat_Weight") double carat_Weight,
                        @Param("color") String color,
                        @Param("clarity") String clarity,
                        @Param("cut") String cut);

        @Query("SELECT dpl FROM DiamondPriceList dpl WHERE dpl.carat_Weight_From <= :caratWeight AND dpl.carat_Weight_To >= :caratWeight AND dpl.color = :color AND dpl.clarity = :clarity AND dpl.cut = :cut AND dpl.origin = :origin")
        List<DiamondPriceList> findPriceListByCriteria(@Param("caratWeight") double caratWeight,
                        @Param("color") String color,
                        @Param("clarity") String clarity,
                        @Param("cut") String cut, @Param("origin") String origin);
}
