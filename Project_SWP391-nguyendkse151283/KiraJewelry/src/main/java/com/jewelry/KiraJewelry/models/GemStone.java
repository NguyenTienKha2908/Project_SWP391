package com.jewelry.KiraJewelry.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "GemStone")
public class GemStone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gem_Id")
    private int gem_Id;

    @Column(name="Gem_Code")
    private String gem_Code;

    @Column(name="Origin")
    private String origin;

    @Column(name="Carat_Weight")
    private String carat_Weight;

    @Column(name="Color")
    private String color;

    @Column(name="Clarity")
    private String clarity;

    @Column(name="Cut")
    private String cut;

    @Column(name = "Proportions")
    private String proportions;

    @Column(name="Polish")
    private String polish;

    @Column(name="Symmetry")
    private String symmetry;

    @Column(name="Fluorescence")
    private String fluorescense;

    @Column(name="Status")
    private boolean status;
}
