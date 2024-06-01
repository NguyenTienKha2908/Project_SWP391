package com.example.demo.entity;

import com.example.demo.validation.PastOrPresentDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.util.Date;

@Entity
@Table(name = "MaterialPriceList")
public class MaterialPriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Min(value = 0, message = "The price must be greater than or equal to 0")
    @Column(name = "Price", nullable = false)
    private double price;

    @PastOrPresentDate
    @Column(name = "Eff_Date", nullable = false)
    private Date effDate;

    @ManyToOne
    @JoinColumn(name = "Material_Id", nullable = false)
    private Material material;

    // Getters and setters
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
