package com.jewelry.KiraJewelry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jewelry.KiraJewelry.models.Product;
import com.jewelry.KiraJewelry.models.ProductDesign;
//da get
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT MAX(p.product_Id) FROM Product p")
    Integer findMaxProductId();

    @Query("SELECT MAX(p.product_Code) FROM Product p")
    String findMaxProductCode();

    // @Query("SELECT p FROM Product p WHERE p.product.product_Id = :product_Id")
    // Product getProductByProductId(int product_Id);

    @Query("SELECT p FROM Product p WHERE p.collection.collection_Id = :collection_Id")
    List<Product> getProductByCollectionId(@Param("collection_Id") int collection_Id);
}

