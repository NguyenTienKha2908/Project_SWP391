package com.jewelry.KiraJewelry.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Production_Order")
public class ProductionOrder {
    @Id
    @Column(name = "production_Order_Id")
    private String production_Order_Id;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "customer_Id")
    private String customer_Id;

    @Column(name = "category_id")
    private int category_id;

    @Column(name = "product_Size")
    private int product_Size;

    @Column(name = "img_Url")
    private String img_Url;

    @Column(name = "description")
    private String description;

    @Column(name = "Q_diamond_Amount")
    private double q_Diamond_Amount;

    @Column(name = "Q_material_Amount")
    private double q_Material_Amount;

    @Column(name = "Q_production_Amount")
    private double q_Production_Amount;

    @Column(name = "Q_total_Amount")
    private double q_Total_Amount;

    @Column(name = "O_diamond_Amount")
    private double o_Diamond_Amount;

    @Column(name = "O_material_Amount")
    private double o_Material_Amount;

    @Column(name = "O_production_Amount")
    private double o_Production_Amount;

    @Column(name = "O_total_Amount")
    private double o_Total_Amount;

    @Column(name = "sales_Staff_Id")
    private String sales_Staff_Id;

    @Column(name = "design_Staff_Id")
    private String design_Staff_Id;

    @Column(name = "production_Staff_Id")
    private String production_Staff_Id;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "product_Id")
    private Product product;
}
