package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
