package com.example.demo.repository;

import com.example.demo.entity.DiamondPriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface DiamondPriceListRepository extends JpaRepository<DiamondPriceList, Integer> {

    @Query("SELECT d FROM DiamondPriceList d WHERE d.origin = :origin")
    List<DiamondPriceList> findByOrigin(@Param("origin") String origin);

    @Query("SELECT DISTINCT d.color FROM DiamondPriceList d WHERE d.origin = :origin AND :caratWeight BETWEEN d.carat_weight_from AND d.carat_weight_to")
    List<String> findDistinctColorsByOriginAndCaratWeight(@Param("origin") String origin, @Param("caratWeight") float caratWeight);

    @Query("SELECT DISTINCT d.clarity FROM DiamondPriceList d WHERE d.origin = :origin AND :caratWeight BETWEEN d.carat_weight_from AND d.carat_weight_to AND d.color = :color")
    List<String> findDistinctClaritiesByOriginCaratWeightAndColor(@Param("origin") String origin, @Param("caratWeight") float caratWeight, @Param("color") String color);

    @Query("SELECT DISTINCT d.cut FROM DiamondPriceList d WHERE d.origin = :origin AND :caratWeight BETWEEN d.carat_weight_from AND d.carat_weight_to AND d.color = :color AND d.clarity = :clarity")
    List<String> findDistinctCutsByOriginCaratWeightColorAndClarity(@Param("origin") String origin, @Param("caratWeight") float caratWeight, @Param("color") String color, @Param("clarity") String clarity);

    @Query("SELECT d FROM DiamondPriceList d WHERE d.origin = :origin AND :caratWeight BETWEEN d.carat_weight_from AND d.carat_weight_to AND d.color = :color AND d.clarity = :clarity AND d.cut = :cut")
    DiamondPriceList findByOriginCaratWeightColorClarityAndCut(@Param("origin") String origin, @Param("caratWeight") float caratWeight, @Param("color") String color, @Param("clarity") String clarity, @Param("cut") String cut);
}
