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
    @Query("SELECT d FROM Diamond d WHERE d.product.product_Id = :productId")
    Diamond findDiamondByProductId(@Param("productId") int productId);

    @Query("SELECT d FROM Diamond d WHERE d.dia_Id= :dia_Id")
    Diamond findDiamondById(@Param("dia_Id") int dia_Id);

    @Query("SELECT d FROM Diamond d WHERE d.dia_Name LIKE %:name%")
    List<Diamond> findByNameContaining(@Param("name") String name);

    @Query("SELECT d FROM Diamond d WHERE d.dia_Name LIKE %:name% AND d.carat_Weight = :caratWeight AND d.color LIKE %:color% AND d.clarity LIKE %:clarity% AND d.cut LIKE %:cut% AND d.origin LIKE %:origin%")
    List<Diamond> findByListDiamonds(@Param("name") String name, @Param("caratWeight") double caratWeight,
            @Param("color") String color,
            @Param("clarity") String clarity,
            @Param("cut") String cut, @Param("origin") String origin);

    @Query("SELECT d FROM Diamond d WHERE d.dia_Name LIKE %:name%  AND d.color LIKE %:color% AND d.clarity LIKE %:clarity% AND d.cut LIKE %:cut% AND d.origin LIKE %:origin%")
    List<Diamond> findByListDiamondsLackWeight(@Param("name") String name,
            @Param("color") String color,
            @Param("clarity") String clarity,
            @Param("cut") String cut, @Param("origin") String origin);
}
