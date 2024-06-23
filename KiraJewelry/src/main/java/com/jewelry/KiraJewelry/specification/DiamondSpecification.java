package com.jewelry.KiraJewelry.specification;

import org.springframework.data.jpa.domain.Specification;

import com.jewelry.KiraJewelry.models.Diamond;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class DiamondSpecification {

    public static Specification<Diamond> findByCriteria(String name, Double caratWeight, String color, String clarity,
            String cut, String origin) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("dia_Name")), "%" + name.toLowerCase() + "%"));
            }
            if (caratWeight != null) {
                predicates.add(cb.equal(root.get("carat_Weight"), caratWeight));
            }
            if (color != null && !color.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("color")), color.toLowerCase()));
            }
            if (clarity != null && !clarity.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("clarity")), "%" + clarity.toLowerCase() + "%"));
            }
            if (cut != null && !cut.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("cut")), "%" + cut.toLowerCase() + "%"));
            }
            if (origin != null && !origin.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("origin")), "%" + origin.toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}