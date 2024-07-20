package com.example.CustomerView.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Product_Material")
public class ProductMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_Material_Id", nullable = false)
    private int product_material_id;

    @Column(name = "Product_Id", nullable = false)
    private int product_id;

    @Column(name = "Material_Id", nullable = false)
    private int material_id;

    @Column(name = "Material_Weight", nullable = false)
    private int material_weight;

    @Column(name = "Q_Price")
    private float q_price;

    @Column(name = "O_Price")
    private float o_price;

    // Getters and setters
    public int getProduct_Material_Id() {
        return product_material_id;
    }

    public void setProduct_Material_Id(int product_material_id) {
        this.product_material_id = product_material_id;
    }

    public int getProduct_Id() {
        return product_id;
    }

    public void setProduct_Id(int product_id) {
        this.product_id = product_id;
    }

    public int getMaterial_Id() {
        return material_id;
    }

    public void setMaterial_Id(int material_id) {
        this.material_id = material_id;
    }

    public int getMaterial_Weight() {
        return material_weight;
    }

    public void setMaterial_Weight(int material_weight) {
        this.material_weight = material_weight;
    }

    public float getQ_Price() {
        return q_price;
    }

    public void setQ_Price(float q_price) {
        this.q_price = q_price;
    }

    public float getO_Price() {
        return o_price;
    }

    public void setO_Price(float o_price) {
        this.o_price = o_price;
    }
}
