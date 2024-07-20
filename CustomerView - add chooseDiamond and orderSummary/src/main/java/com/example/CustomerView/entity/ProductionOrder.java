package com.example.CustomerView.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

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
    private float q_diamond_amount;
    
    @Column(name = "Q_Material_Amount")
    private float q_material_amount;
    
    @Column(name = "Q_Production_Amount")
    private float q_production_amount;
    
    @Column(name = "Q_Total_Amount")
    private float q_total_amount;
    
    @Column(name = "O_Diamond_Amount")
    private float o_diamond_amount;
    
    @Column(name = "O_Material_Amount")
    private float o_material_amount;
    
    @Column(name = "O_Production_Amount")
    private float o_production_amount;
    
    @Column(name = "O_Total_Amount")
    private float o_total_amount;
    
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

    // Getters and Setters

    public String getProduction_order_id() {
        return production_order_id;
    }

    public void setProduction_order_id(String production_order_id) {
        this.production_order_id = production_order_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getProduct_size() {
        return product_size;
    }

    public void setProduct_size(int product_size) {
        this.product_size = product_size;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getQ_diamond_amount() {
        return q_diamond_amount;
    }

    public void setQ_diamond_amount(float q_diamond_amount) {
        this.q_diamond_amount = q_diamond_amount;
    }

    public float getQ_material_amount() {
        return q_material_amount;
    }

    public void setQ_material_amount(float q_material_amount) {
        this.q_material_amount = q_material_amount;
    }

    public float getQ_production_amount() {
        return q_production_amount;
    }

    public void setQ_production_amount(float q_production_amount) {
        this.q_production_amount = q_production_amount;
    }

    public float getQ_total_amount() {
        return q_total_amount;
    }

    public void setQ_total_amount(float q_total_amount) {
        this.q_total_amount = q_total_amount;
    }

    public float getO_diamond_amount() {
        return o_diamond_amount;
    }

    public void setO_diamond_amount(float o_diamond_amount) {
        this.o_diamond_amount = o_diamond_amount;
    }

    public float getO_material_amount() {
        return o_material_amount;
    }

    public void setO_material_amount(float o_material_amount) {
        this.o_material_amount = o_material_amount;
    }

    public float getO_production_amount() {
        return o_production_amount;
    }

    public void setO_production_amount(float o_production_amount) {
        this.o_production_amount = o_production_amount;
    }

    public float getO_total_amount() {
        return o_total_amount;
    }

    public void setO_total_amount(float o_total_amount) {
        this.o_total_amount = o_total_amount;
    }

    public String getSales_staff_id() {
        return sales_staff_id;
    }

    public void setSales_staff_id(String sales_staff_id) {
        this.sales_staff_id = sales_staff_id;
    }

    public String getDesign_staff_id() {
        return design_staff_id;
    }

    public void setDesign_staff_id(String design_staff_id) {
        this.design_staff_id = design_staff_id;
    }

    public String getProduction_staff_id() {
        return production_staff_id;
    }

    public void setProduction_staff_id(String production_staff_id) {
        this.production_staff_id = production_staff_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
