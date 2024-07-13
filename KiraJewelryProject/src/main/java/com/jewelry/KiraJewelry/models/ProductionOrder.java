package com.jewelry.KiraJewelry.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "production_order")
public class ProductionOrder {
    @Id
    @Column(name = "production_order_id")
    private String production_Order_Id;

    @Column(name = "date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @OneToOne
    @JoinColumn(name="category_id")
    private Category category;

    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @Column(name = "product_size")
    private int product_Size;

    @Column(name = "description")
    private String description;

    @Column(name = "q_diamond_amount")
    private double q_Diamond_Amount;

    @Column(name = "q_material_amount")
    private double q_Material_Amount;

    @Column(name = "q_production_amount")
    private double q_Production_Amount;

    @Column(name = "q_total_amount")
    private double q_Total_Amount;

    @Column(name = "o_diamond_amount")
    private double o_Diamond_Amount;

    @Column(name = "o_material_amount")
    private double o_Material_Amount;

    @Column(name = "o_production_amount")
    private double o_Production_Amount;

    @Column(name = "o_total_amount")
    private double o_Total_Amount;

    @Column(name = "sales_staff_id")
    private String sales_Staff;

    @Column(name = "design_staff_id")
    private String design_Staff;

    @Column(name = "production_staff_id")
    private String production_Staff;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
