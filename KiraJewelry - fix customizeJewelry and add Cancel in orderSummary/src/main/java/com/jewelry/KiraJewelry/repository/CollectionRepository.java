package com.jewelry.KiraJewelry.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.Collection;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Integer> {
}

