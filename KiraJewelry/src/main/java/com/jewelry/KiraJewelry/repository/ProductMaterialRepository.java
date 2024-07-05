package com.jewelry.KiraJewelry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.ProductMaterial;
import com.jewelry.KiraJewelry.models.ProductMaterialId;

@Repository
public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, ProductMaterialId> {
    @Query("SELECT pm FROM ProductMaterial pm WHERE pm.id.product_Id = :product_Id AND pm.id.material_Id = :material_Id")
    ProductMaterial findByIdProductIdAndIdMaterialId(@Param("product_Id") int productId,
            @Param("material_Id") int materialId);

    @Query("SELECT p FROM ProductMaterial p WHERE p.id.product_Id = :product_Id")
    ProductMaterial findProductMaterialByProductId(@Param("product_Id") int productId);

    @Query("SELECT p FROM ProductMaterial p WHERE p.id.product_Id = :product_Id")
    List<ProductMaterial> findListProductMaterialByProductId(@Param("product_Id") int productId);

    @Query("SELECT pm FROM ProductMaterial pm WHERE pm.id.product_Id = :product_Id")
    ProductMaterial findByProduct_Id(@Param("product_Id") int productId);

    @Query("SELECT MAX(pm.id.product_Id) FROM ProductMaterial pm")
    Integer findMaxProductMaterialId();

    @Query("SELECT pm FROM ProductMaterial pm WHERE pm.id.product_Id = :product_Id")
    ProductMaterial findFirstByProductId(@Param("product_Id") int productId);

    
}
