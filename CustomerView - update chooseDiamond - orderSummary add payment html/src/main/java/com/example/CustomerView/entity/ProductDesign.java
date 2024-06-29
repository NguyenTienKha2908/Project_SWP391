package com.example.CustomerView.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product_Design")
public class ProductDesign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_Design_Id")
    private int product_Design_Id;

    @NotBlank(message = "Product design code is mandatory")
    @Column(name = "Product_Design_Code", nullable = false)
    private String product_Design_Code;

    @Column(name = "Product_Id", nullable = false)
    private int product_Id;

    @Column(name = "Product_Design_Name", nullable = true)
    private String product_Design_Name;

    @Column(name = "Category_Id", nullable = false)
    private int category_Id;

    @Column(name = "Collection_Id", nullable = true)
    private Integer collection_Id;

    @Column(name = "Description", nullable = true)
    private String description;

    @Column(name = "Gender", nullable = true)
    private String gender;

    @Column(name = "Product_Size", nullable = true)
    private Integer product_Size;

    @Column(name = "Product_Design_Shell_Id", nullable = true)
    private Integer product_Design_Shell_Id;

    @Column(name = "Gem_Min_Size", nullable = true)
    private Double gem_Min_Size;

    @Column(name = "Gem_Max_Size", nullable = true)
    private Double gem_Max_Size;

    @Column(name = "Status", nullable = true)
    private Boolean status;
}
