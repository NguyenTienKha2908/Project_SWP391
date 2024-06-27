package com.jewelry.KiraJewelry.models;

import jakarta.persistence.Entity;
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
    @JoinColumn(name = "Product_Id")
    private int product_Id;
    
    @Id
    @JoinColumn(name = "Material_Id")
    private int material_Id;

    @JoinColumn(name="Material_Weight")
    private int material_Weight;
}
