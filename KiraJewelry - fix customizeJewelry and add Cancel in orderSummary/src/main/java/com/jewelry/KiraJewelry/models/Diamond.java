package com.jewelry.KiraJewelry.models;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Diamond")
public class Diamond {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dia_id")
    private int dia_Id;

    @NotBlank(message = "Diamond code is mandatory")
    @Column(name = "dia_code", nullable = false)
    private String dia_Code;

    @NotBlank(message = "Diamond name is mandatory")
    @Column(name = "dia_name", nullable = false)
    private String dia_Name;

    @NotBlank(message = "Origin is mandatory")
    @Column(name = "origin", nullable = false)
    private String origin;

    @NotNull(message = "Carat weight is mandatory")
    @Column(name = "carat_weight", nullable = false)
    private double carat_Weight;

    @NotBlank(message = "Color is mandatory")
    @Column(name = "color", nullable = false)
    private String color;

    @NotBlank(message = "Clarity is mandatory")
    @Column(name = "clarity", nullable = false)
    private String clarity;

    @NotBlank(message = "Cut is mandatory")
    @Column(name = "cut", nullable = false)
    private String cut;

    @NotBlank(message = "Proportions are mandatory")
    @Column(name = "proportions", nullable = false)
    private String proportions;

    @NotBlank(message = "Polish is mandatory")
    @Column(name = "polish", nullable = false)
    private String polish;

    @NotBlank(message = "Symmetry is mandatory")
    @Column(name = "symmetry", nullable = false)
    private String symmetry;

    @NotBlank(message = "Fluorescence is mandatory")
    @Column(name = "fluorescence", nullable = false)
    private String fluorescence;

    @Column(name = "status", nullable = false)
    private boolean status; // 1 for active, meaning available, 0 for inactive, meanig=ng inavailable

    @NotNull(message = "Q Price is mandatory")
    @Column(name = "q_price", nullable = false)
    private double q_Price;

    @NotNull(message = "O Price is mandatory")
    @Column(name = "o_price", nullable = false)
    private double o_Price;

    @OneToOne
    @JoinColumn(name = "product_Id", nullable = true)
    private Product product;
}
