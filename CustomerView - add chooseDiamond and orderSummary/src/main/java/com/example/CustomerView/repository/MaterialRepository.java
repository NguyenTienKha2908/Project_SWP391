package com.example.CustomerView.repository;

import com.example.CustomerView.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Integer> {

    @Query("SELECT m FROM Material m WHERE m.material_Id IN (SELECT pds.material_Id FROM ProductDesignShell pds WHERE pds.product_Design_Shell_Id = :productDesignShellId)")
    List<Material> findMaterialsForProductDesignShell(@Param("productDesignShellId") int productDesignShellId);

    @Query("SELECT m FROM Material m WHERE m.material_Id IN (SELECT pds.material_Id FROM ProductDesignShell pds)")
    List<Material> findAllMaterialsInProductDesignShell();

}
