package com.jewelry.KiraJewelry.models;

import jakarta.persistence.*;

@Entity
@Table(name = "product_design_shell")
public class ProductDesignShell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_design_shell_id", nullable = false)
    private int product_Design_Shell_Id;

    @Column(name = "material_id", nullable = false)
    private int material_Id;

    @Column(name = "weight", nullable = false)
    private int weight;

    // Getters and setters
    public int getProduct_Design_Shell_Id() {
        return product_Design_Shell_Id;
    }

    public void setProduct_Design_Shell_Id(int product_Design_Shell_Id) {
        this.product_Design_Shell_Id = product_Design_Shell_Id;
    }

    public int getMaterial_Id() {
        return material_Id;
    }

    public void setMaterial_Id(int material_Id) {
        this.material_Id = material_Id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
