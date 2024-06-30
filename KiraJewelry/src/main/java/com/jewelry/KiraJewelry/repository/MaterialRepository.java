package com.jewelry.KiraJewelry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    @Query("SELECT m FROM Material m WHERE m.material_Name = :material_Name")
    Material findMaterialByName(@Param("material_Name") String material_Name);

    List<Material> findByStatus(int status);

    @Query("SELECT m FROM Material m WHERE m.material_Id IN (SELECT pds.material_Id FROM ProductDesignShell pds WHERE pds.product_Design_Shell_Id = :productDesignShellId)")
    List<Material> findMaterialsForProductDesignShell(@Param("productDesignShellId") int productDesignShellId);

    @Query("SELECT m FROM Material m WHERE m.material_Id IN (SELECT pds.material_Id FROM ProductDesignShell pds)")
    List<Material> findAllMaterialsInProductDesignShell();

    @Query("SELECT m FROM Material m WHERE m.material_Name LIKE %:name%")
    List<Material> findByNameContaining(@Param("name") String name);

}
