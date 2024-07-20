package com.example.CustomerView.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Diamond")
public class Diamond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Dia_Id", nullable = false)
    private int dia_Id;

    @Column(name = "Dia_Code", nullable = false)
    private String dia_Code;

    @Column(name = "Dia_Name", nullable = false)
    private String dia_Name;

    @Column(name = "Origin", nullable = false)
    private String origin;

    @Column(name = "Carat_Weight", nullable = false)
    private float carat_Weight;

    @Column(name = "Color", nullable = false)
    private String color;

    @Column(name = "Clarity", nullable = false)
    private String clarity;

    @Column(name = "Cut", nullable = false)
    private String cut;

    @Column(name = "Proportions", nullable = false)
    private String proportions;

    @Column(name = "Polish", nullable = false)
    private String polish;

    @Column(name = "Symmetry", nullable = false)
    private String symmetry;

    @Column(name = "Fluorescence", nullable = false)
    private String fluorescence;

    @Column(name = "Status", nullable = false)
    private boolean status;

    @Column(name = "Img_Url", nullable = false)
    private String img_Url;

    @Column(name = "Q_Price", nullable = false)
    private float q_Price;

    @Column(name = "O_Price", nullable = false)
    private float o_Price;

    @Column(name = "Product_Id", nullable = false)
    private Integer product_Id;
}
