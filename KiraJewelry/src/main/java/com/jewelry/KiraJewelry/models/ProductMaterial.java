package com.jewelry.KiraJewelry.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name = "Product_Material")
public class ProductMaterial {

    @EmbeddedId
    private ProductMaterialId id;

    @JoinColumn(name = "Material_Weight")
    private double material_Weight;

    @Column(name = "Q_Price")
    private double q_Price;

    @Column(name = "O_Price")
    private double o_Price;
}
