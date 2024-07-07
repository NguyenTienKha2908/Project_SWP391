package com.jewelry.KiraJewelry.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_Id")
    private int product_Id;

    @Column(name = "product_Name", nullable = true)
    private String product_Name;

    @NotBlank(message = "Product code is mandatory")
    @Column(name = "product_Code", nullable = false)
    private String product_Code;

    @OneToOne
    @JoinColumn(name = "Category_Id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "Collection_Id")
    private Collection collection;

    @Column(name = "Description", nullable = true)
    private String description;

    @Column(name = "Gender", nullable = true)
    private String gender;

    @Column(name = "Size", nullable = true)
    private Integer size;

    @Column(name = "Status", nullable = true)
    private boolean status;

    // @Column(name = "Img_Url", nullable = true)
    // private String img_Url;

}
