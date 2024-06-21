package com.jewelry.KiraJewelry.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(name = "Diamond_Price_List")
public class Diamond_Price_List {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Origin")
    private String origin;

    @Column(name = "Carat_Weight_From")
    private double carat_Weight_From;

    @Column(name = "Carat_Weight_To")
    private double carat_Weight_To;

    @Column(name = "Color")
    private String color;

    @Column(name = "Clarity")
    private String clarity;

    @Column(name = "Cut")
    private String cut;

    @Column(name = "Price")
    private double price;

    @Column(name = "Eff_Date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eff_Date;
}
