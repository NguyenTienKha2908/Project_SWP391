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
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_Id")
    private int product_Id;

    @NotBlank(message = "Product code is mandatory")
    @Column(name = "Product_Code", nullable = false)
    private String product_Code;

    @Column(name = "Product_Name", nullable = true)
    private String product_Name;

    @Column(name = "Collection_Id", nullable = true)
    private Integer collection_Id;

    @Column(name = "Category_Id", nullable = false)
    private int category_Id;

    @Column(name = "Description", nullable = true)
    private String description;

    @Column(name = "Gender", nullable = true)
    private String gender;

    @Column(name = "Size", nullable = true)
    private Integer size;

    @Column(name = "Img_Url", nullable = true)
    private String img_Url;

    @Column(name = "Status", nullable = true)
    private Boolean status;
}
