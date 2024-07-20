package com.jewelry.KiraJewelry.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
}

