package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.GemStone;
import java.util.List;

public interface GemStoneRepository extends JpaRepository<GemStone, Integer> {
    List<GemStone> findByStatus(int status);
}
