package com.jewelry.KiraJewelry.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
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

   @EmbeddedId
    private ProductMaterialId id;

    @JoinColumn(name="Material_Weight")
    private Float material_Weight;

    @Column(name = "Q_Price")
    private float q_price;

    @Column(name = "O_Price")
    private float o_price;

    public Float getMaterial_Weight() {
        return material_Weight;
    }

    public void setMaterial_Weight(Float material_Weight) {
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
