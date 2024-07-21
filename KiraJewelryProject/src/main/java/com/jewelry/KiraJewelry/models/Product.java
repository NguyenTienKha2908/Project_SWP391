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
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int product_Id;

    @Column(name = "product_name", nullable = true)
    private String product_Name;

    @NotBlank(message = "Product code is mandatory")
    @Column(name = "product_code", nullable = false)
    private String product_Code;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "gender", nullable = true)
    private String gender;

    @Column(name = "size", nullable = true)
    private Integer size;

    @Column(name = "status", nullable = true)
    private boolean status;

    // @Column(name = "Img_Url", nullable = true)
    // private String img_Url;

}
