package com.jewelry.KiraJewelry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jewelry.KiraJewelry.models.ProductDesign;

public interface ProductDesignRepository extends JpaRepository<ProductDesign, Integer> {

    @Query("SELECT MAX(pd.product_Design_Id) FROM ProductDesign pd")
    Integer findMaxProductDesignId();

    @Query("SELECT MAX(pd.product_Design_Code) FROM ProductDesign pd")
    String findMaxProductDesignCode();

    @Query("SELECT pd FROM ProductDesign pd WHERE pd.product_Id = :productId")
    ProductDesign findByProductId(@Param("productId") int productId);

    @Query("SELECT pd FROM ProductDesign pd WHERE pd.collection_Id = :collection_Id")
    List<ProductDesign> getProductDesignByCollectionId(@Param("collection_Id") int collection_Id);
}
