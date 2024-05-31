package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Date;


@Entity
@Table(name = "MaterialPriceList")  // Explicitly define the table name
public class MaterialPriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Explicit definition may not be necessary if naming matches
    private int id;

    @Column(name = "Price", nullable = false)  // Match the case and name
    private double price;

    @Column(name = "EffDate", nullable = false)  // Match the case and name
    private Date effDate;

    @ManyToOne
    @JoinColumn(name = "MaterialId", nullable = false)  // Ensure this matches the case and column name in Material table
    private Material material;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    
}


    

