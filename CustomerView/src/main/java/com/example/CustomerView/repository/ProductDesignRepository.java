package com.example.CustomerView.repository;

import com.example.CustomerView.entity.ProductDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductDesignRepository extends JpaRepository<ProductDesign, Integer> {
    @Query("SELECT MAX(pd.product_Design_Id) FROM ProductDesign pd")
    Integer findMaxProductDesignId();

    @Query("SELECT MAX(pd.product_Design_Code) FROM ProductDesign pd")
    String findMaxProductDesignCode();
}
