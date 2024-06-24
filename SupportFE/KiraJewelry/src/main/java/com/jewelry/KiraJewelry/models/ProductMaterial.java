package com.jewelry.KiraJewelry.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "ProductMaterial")
public class ProductMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_Material_Id", nullable = false)
    private int product_material_id;
  
    @JoinColumn(name = "Product_Id")
    private int product_Id;
    
    @JoinColumn(name = "Material_Id")
    private int material_Id;

    @JoinColumn(name="Material_Weight")
    private int material_Weight;

    @Column(name = "Q_Price")
    private float q_price;

    @Column(name = "O_Price")
    private float o_price;

    // Getters and setters
    public int getProductMaterial_Id() {
        return product_material_id;
    }

    public void setProductMaterial_Id(int product_material_id) {
        this.product_material_id = product_material_id;
    }

    public int getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(int product_Id) {
        this.product_Id = product_Id;
    }

    public int getMaterial_Id() {
        return material_Id;
    }

    public void setMaterial_Id(int material_Id) {
        this.material_Id = material_Id;
    }

    public int getMaterial_Weight() {
        return material_Weight;
    }

    public void setMaterial_Weight(int material_Weight) {
        this.material_Weight = material_Weight;
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
