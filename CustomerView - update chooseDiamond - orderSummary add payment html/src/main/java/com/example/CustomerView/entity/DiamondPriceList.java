package com.example.CustomerView.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Diamond_Price_List")
public class DiamondPriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "origin")
    private String origin;

    @Column(name = "carat_weight_from")
    private Float carat_Weight_From;

    @Column(name = "carat_weight_to")
    private Float carat_Weight_To;

    @Column(name = "color")
    private String color;

    @Column(name = "clarity")
    private String clarity;

    @Column(name = "cut")
    private String cut;

    @Min(value = 0, message = "The price must be greater than or equal to 0")
    @Column(name = "price")
    private float price;

    @PastOrPresent
    @Column(name = "eff_date")
    private Date eff_Date;
}
