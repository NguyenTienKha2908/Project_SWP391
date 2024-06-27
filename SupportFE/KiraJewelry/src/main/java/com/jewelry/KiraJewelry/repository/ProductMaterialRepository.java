package com.jewelry.KiraJewelry.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.jewelry.KiraJewelry.models.ProductMaterial;
import com.jewelry.KiraJewelry.models.ProductMaterialId;

public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, ProductMaterialId> {
    @Query("SELECT p FROM ProductMaterial p WHERE p.id.product_Id= :product_Id")
    ProductMaterial findProductMaterialByProductId(@Param("product_Id") int product_Id);

    @Query("SELECT p FROM ProductMaterial p WHERE p.id.product_Id = :productId")
    List<ProductMaterial> findListProductMaterialByProductId(@Param("productId") int productId);

    // @Query("SELECT p FROM ProductMaterial p WHERE p.id.product_Id= :product_Id")
    // List<ProductMaterial> findListProductMaterialByProductId(@Param("product_Id") int product_Id);

}