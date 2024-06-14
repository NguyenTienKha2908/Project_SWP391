package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Diamond;

import java.util.List;

public interface DiamondRepository extends JpaRepository<Diamond, Integer> {
    List<Diamond> findByStatus(int status);
}
