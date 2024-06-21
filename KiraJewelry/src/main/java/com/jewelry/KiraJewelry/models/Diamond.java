package com.jewelry.KiraJewelry.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "Diamond")
public class Diamond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dia_Id")
    private int dia_Id;

    @Column(name = "dia_Code")
    private String dia_Code;

    @Column(name = "dia_Name")
    private String dia_Name;

    @Column(name = "Origin")
    private String origin;

    @Column(name = "Carat_Weight")
    private double carat_Weight;

    @Column(name = "Color")
    private String color;

    @Column(name = "Clarity")
    private String clarity;

    @Column(name = "Cut")
    private String cut;

    @Column(name = "Proportions")
    private String proportions;

    @Column(name = "Polish")
    private String polish;

    @Column(name = "Symmetry")
    private String symmetry;

    @Column(name = "Fluorescence")
    private String fluorescence;

    @Column(name = "Status")
    private boolean status;

    @Column(name = "Img_Url")
    private String img_Url;

    @Column(name = "Q_Price")
    private double q_Price;

    @Column(name = "O_Price")
    private double o_Price;

    @OneToOne
    @JoinColumn(name = "Product_Id")
    private Product product;
}
