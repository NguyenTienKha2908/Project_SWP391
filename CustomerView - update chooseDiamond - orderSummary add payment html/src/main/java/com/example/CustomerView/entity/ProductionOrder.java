package com.example.CustomerView.entity;

import jakarta.persistence.*;
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
@Table(name = "Production_Order")
public class ProductionOrder {
    
    @Id
    @Column(name = "Production_Order_Id")
    private String production_order_id;
    
    @Column(name = "Date")
    private Date date;
    
    @Column(name = "Customer_Id")
    private String customer_id;
    
    @Column(name = "Category_Id")
    private int category_id;
    
    @Column(name = "Product_Size")
    private int product_size;
    
    @Column(name = "Img_Url")
    private String img_url;
    
    @Column(name = "Description")
    private String description;
    
    @Column(name = "Q_Diamond_Amount")
    private double q_diamond_amount;
    
    @Column(name = "Q_Material_Amount")
    private double q_material_amount;
    
    @Column(name = "Q_Production_Amount")
    private double q_production_amount;
    
    @Column(name = "Q_Total_Amount")
    private double q_total_amount;
    
    @Column(name = "O_Diamond_Amount")
    private double o_diamond_amount;
    
    @Column(name = "O_Material_Amount")
    private double o_material_amount;
    
    @Column(name = "O_Production_Amount")
    private double o_production_amount;
    
    @Column(name = "O_Total_Amount")
    private double o_total_amount;
    
    @Column(name = "Sales_Staff_Id")
    private String sales_staff_id;
    
    @Column(name = "Design_Staff_Id")
    private String design_staff_id;
    
    @Column(name = "Production_Staff_Id")
    private String production_staff_id;
    
    @Column(name = "Status")
    private int status;
    
    @Column(name = "Product_Id")
    private int product_id;
}
