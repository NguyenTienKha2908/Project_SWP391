package com.example.CustomerView.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private double q_price;

    @Column(name = "O_Price")
    private double o_price;
}
