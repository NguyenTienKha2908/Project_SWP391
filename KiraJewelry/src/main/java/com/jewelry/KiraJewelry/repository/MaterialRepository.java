package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    @Query("SELECT m FROM Material m WHERE m.material_Name = :material_Name")
    Material findMaterialByName(@Param("material_Name") String material_Name);

}
