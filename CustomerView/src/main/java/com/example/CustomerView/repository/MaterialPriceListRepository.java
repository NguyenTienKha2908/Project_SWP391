package com.example.CustomerView.repository;

import com.example.CustomerView.entity.MaterialPriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialPriceListRepository extends JpaRepository<MaterialPriceList, Integer> {
    
    @Query("SELECT mpl FROM MaterialPriceList mpl WHERE mpl.material.material_Id = :materialId")
    MaterialPriceList findTopByMaterialId(@Param("materialId") int materialId);
}
