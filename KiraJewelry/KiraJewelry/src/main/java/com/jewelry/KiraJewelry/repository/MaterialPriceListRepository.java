package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.MaterialPriceList;

@Repository
public interface MaterialPriceListRepository extends JpaRepository<MaterialPriceList, Integer> {
    // boolean existsByMaterial_MaterialId(int materialId);
    @Query("SELECT m FROM MaterialPriceList m WHERE m.material.material_Id= :material_Id")
    MaterialPriceList findMaterialPriceListByMaterialId(@Param("material_Id") int material_Id);
    
    @Query("SELECT mpl FROM MaterialPriceList mpl WHERE mpl.material.material_Id = :materialId")
    MaterialPriceList findTopByMaterialId(@Param("materialId") int materialId);
}