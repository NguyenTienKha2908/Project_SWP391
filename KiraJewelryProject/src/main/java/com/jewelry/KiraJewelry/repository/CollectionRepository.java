package com.jewelry.KiraJewelry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.Collection;
import com.jewelry.KiraJewelry.models.Product;
import com.jewelry.KiraJewelry.models.ProductDesign;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Integer> {
}
