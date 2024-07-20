package com.example.CustomerView.repository;

import com.example.CustomerView.entity.ProductDesignShell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductDesignShellRepository extends JpaRepository<ProductDesignShell, Integer> {

    @Query("SELECT pds FROM ProductDesignShell pds WHERE pds.material_Id = :materialId AND pds.weight = :weight")
    ProductDesignShell findByMaterialIdAndWeight(@Param("materialId") int materialId, @Param("weight") int weight);
}
