package com.example.CustomerView.repository;

import com.example.CustomerView.entity.ProductMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Integer> {

    @Query("SELECT pm FROM ProductMaterial pm WHERE pm.product_id = :product_id")
    ProductMaterial findByProduct_Id(@Param("product_id") int product_id);
}
