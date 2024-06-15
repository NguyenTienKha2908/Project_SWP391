package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c.category_Name FROM Category c WHERE c.category_id = :category_id")
    String getCateNameById(@Param("category_id") int category_id);

}
