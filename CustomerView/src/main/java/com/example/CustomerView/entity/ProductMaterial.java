package com.example.CustomerView.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ProductMaterial")
public class ProductMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_Id", nullable = false)
    private int product_Id;

    @Column(name = "Material_Id", nullable = false)
    private int material_Id;

    @Column(name = "Weight", nullable = false)
    private int material_weight;

    @Column(name = "Q_Price")
    private float q_price;

    @Column(name = "O_Price")
    private float o_price;

    public int getProductId() {
        return product_Id;
    }

    public void setProductId(int product_Id) {
        this.product_Id = product_Id;
    }

    public int getMaterialId() {
        return material_Id;
    }

    public void setMaterialId(int material_Id) {
        this.material_Id = material_Id;
    }

    public int getWeight() {
        return material_weight;
    }

    public void setWeight(int material_weight) {
        this.material_weight = material_weight;
    }

    public float getQPrice() {
        return q_price;
    }

    public void setQPrice(float q_price) {
        this.q_price = q_price;
    }

    public float getOPrice() {
        return o_price;
    }

    public void setOPrice(float o_price) {
        this.o_price = o_price;
    }
}
