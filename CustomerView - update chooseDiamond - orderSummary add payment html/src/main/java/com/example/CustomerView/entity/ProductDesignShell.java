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
@Table(name = "Product_Design_Shell")
public class ProductDesignShell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_Design_Shell_Id", nullable = false)
    private int product_Design_Shell_Id;

    @Column(name = "Material_Id", nullable = false)
    private int material_Id;

    @Column(name = "Weight", nullable = false)
    private int weight;
}
