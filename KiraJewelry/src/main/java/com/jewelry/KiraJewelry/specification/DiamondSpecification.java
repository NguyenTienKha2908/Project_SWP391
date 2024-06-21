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
                predicates.add(cb.like(root.get("dia_Name"), "%" + name + "%"));
            }
            if (caratWeight != null) {
                predicates.add(cb.equal(root.get("carat_Weight"), caratWeight));
            }
            if (color != null && !color.isEmpty()) {
                predicates.add(cb.equal(root.get("color"), color));
            }
            if (clarity != null && !clarity.isEmpty()) {
                predicates.add(cb.like(root.get("clarity"), "%" + clarity + "%"));
            }
            if (cut != null && !cut.isEmpty()) {
                predicates.add(cb.like(root.get("cut"), "%" + cut + "%"));
            }
            if (origin != null && !origin.isEmpty()) {
                predicates.add(cb.like(root.get("origin"), "%" + origin + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
