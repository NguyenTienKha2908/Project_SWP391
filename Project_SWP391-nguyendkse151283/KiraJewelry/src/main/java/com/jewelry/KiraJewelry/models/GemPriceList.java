package com.jewelry.KiraJewelry.models;

import java.sql.Date;

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
@Table(name = "Category")
public class GemPriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private String id;

    @Column(name="Origin")
    private String origin;

    @Column(name="Carat_weight")
    private String carat_weight;

    @Column(name="Color")
    private String color;

    @Column(name="Clarity")
    private String clarity;

    @Column(name="Cut")
    private String cut;

    @Column(name="Price")
    private double price;

    @Column(name="Eff_Date")
    private Date eff_Date;
    
}
