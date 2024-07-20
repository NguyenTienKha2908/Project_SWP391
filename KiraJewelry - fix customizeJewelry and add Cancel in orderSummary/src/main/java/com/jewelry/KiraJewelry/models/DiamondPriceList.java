package com.jewelry.KiraJewelry.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Diamond_Price_List")
public class DiamondPriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "origin")
    private String origin;

    @Column(name = "carat_weight_from")
    private double carat_Weight_From;

    @Column(name = "carat_weight_to")
    private double carat_Weight_To;

    @Column(name = "color")
    private String color;

    @Column(name = "clarity")
    private String clarity;

    @Column(name = "cut")
    private String cut;

    @Min(value = 0, message = "The price must be greater than or equal to 0")
    @Column(name = "price")
    private double price;

    @PastOrPresent
    @Column(name = "eff_date")
    private Date eff_Date;

}
