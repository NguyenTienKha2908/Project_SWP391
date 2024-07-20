
package com.example.CustomerView.entity;

import com.example.CustomerView.validation.PastOrPresentDate;
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
    @Column(name = "price", nullable = false)
    private double price;

    @PastOrPresentDate
    @Column(name = "eff_date", nullable = false)
    private Date eff_Date;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
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
        return eff_Date;
    }

    public void setEffDate(Date effDate) {
        this.eff_Date = effDate;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
