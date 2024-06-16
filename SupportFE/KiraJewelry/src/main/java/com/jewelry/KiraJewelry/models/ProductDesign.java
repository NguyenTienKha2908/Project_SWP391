// package com.jewelry.KiraJewelry.models;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Entity
// @NoArgsConstructor
// @AllArgsConstructor
// @Getter
// @Setter
// @Table(name = "Product_Design")
// public class ProductDesign {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "Product_Design_Id")
//     private int product_Design_Id;

//     @Column(name = "Product_Design_Code")
//     private String product_Design_Code;

//     @Column(name = "Product_Design_Name")
//     private String product_Design_Name;

//     @JoinColumn(name = "Category_Id")
//     private Category category;

//     @JoinColumn(name = "Collection_Id")
//     private Collection collections;

//     @Column(name = "Description")
//     private String description;

//     @Column(name = "Gender")
//     private String gender;

//     @Column(name = "Product_Size")
//     private int product_Size;

//     @JoinColumn(name="Product_Design_Shell_Id")
//     private ProductDesignShell product_Design_Shell;

//     @JoinColumn(name="Product_Design_Diamond_Id")
//     private ProductDesignDiamond product_Design_Diamond;

//     @Column(name = "Gem_Min_Size")
//     private double gem_Min_Size;

//     @Column(name = "Gem_Max_Size")
//     private double gem_Max_Size;

// }
