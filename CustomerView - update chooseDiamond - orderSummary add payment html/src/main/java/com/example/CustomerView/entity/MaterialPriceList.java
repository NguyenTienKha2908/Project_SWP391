package com.example.CustomerView.entity;

import com.example.CustomerView.validation.PastOrPresentDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Material_Price_List")
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
}
