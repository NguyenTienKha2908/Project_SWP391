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
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_Id")
    private int product_Id;

    @Column(name="product_Name")
    private String product_Name;

    @Column(name="product_Code")
    private String product_Code;

    @OneToOne
    @JoinColumn(name="Category_Id")
    private Category category;

    @OneToOne
    @JoinColumn(name="Collection_Id")
    private Collection collection;

    @Column(name="Description")
    private String description;

    @Column(name="Gender")
    private String gender;

    @Column(name="Size")
    private String size;

    @Column(name="Status")
    private boolean status;

}
