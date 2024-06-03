package com.jewelry.KiraJewelry.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Production_Order")
public class ProductionOrder {
    @Id
    @Column(name = "production_Order_Id")
    private String production_Order_Id;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "customer_Id")
    private String customer_Id;

    @Column(name = "category_Id")
    private int category_Id;

    @Column(name = "material_Name")
    private String material_Name;

    @Column(name = "material_Color")
    private String material_Color;

    @Column(name = "material_Weight")
    private double material_Weight;

    @Column(name = "material_Id")
    private int material_Id;

    @Column(name = "gem_Name")
    private String gem_Name;

    @Column(name = "gem_Color")
    private String gem_Color;

    @Column(name = "gem_Weight")
    private double gem_Weight;

    @Column(name = "gem_Id")
    private int gem_Id;

    @Column(name = "product_Size")
    private int product_Size;

    @Column(name = "description")
    private String description;

    @Column(name = "diamond_Amount")
    private double diamond_Amount;

    @Column(name = "material_Amount")
    private double material_Amount;

    @Column(name = "production_Amount")
    private double production_Amount;

    @Column(name = "side_Material_Cost")
    private double side_Material_Cost;

    @Column(name = "side_Gem_Cost")
    private double side_Gem_Cost;

    @Column(name = "total_Amount")
    private double total_Amount;

    @Column(name = "sales_Staff_Name")
    private String sales_Staff_Name;

    @Column(name = "design_Staff_Name")
    private String design_Staff_Name;

    @Column(name = "production_Staff_Name")
    private String production_Staff_Name;

    @Column(name = "status")
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "product_Id")
    private Product product;
}
