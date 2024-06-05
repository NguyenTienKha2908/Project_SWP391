// ProductionOrder.java
package com.jewelry.KiraJewelry.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ProductionOrder")
public class ProductionOrder {

    @Id
    @Column(name = "Production_Order_Id")
    private String productionOrderId;

    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "Customer_Id", referencedColumnName = "Customer_Id", insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "Customer_Id")
    private String customerId;

    @Column(name = "Category_Id")
    private int categoryId;

    @Column(name = "Material_Name")
    private String materialName;

    @Column(name = "Material_Color")
    private String materialColor;

    @Column(name = "Material_Weight")
    private double materialWeight;

    @Column(name = "Material_Id")
    private Integer materialId;

    @Column(name = "Gem_Name")
    private String gemName;

    @Column(name = "Gem_Color")
    private String gemColor;

    @Column(name = "Gem_Weight")
    private double gemWeight;

    @Column(name = "Gem_Id")
    private Integer gemId;

    @Column(name = "Product_Size")
    private int productSize;

    @Column(name = "Description")
    private String description;

    @Column(name = "Diamond_Amount")
    private Double diamondAmount;

    @Column(name = "Material_Amount")
    private Double materialAmount;

    @Column(name = "Production_Amount")
    private Double productionAmount;

    @Column(name = "Side_Material_Cost")
    private Double sideMaterialCost;

    @Column(name = "Side_Gem_Cost")
    private Double sideGemCost;

    @Column(name = "Total_Amount")
    private Double totalAmount;

    @Column(name = "Sales_Staff_Name")
    private String salesStaffName;

    @Column(name = "Design_Staff_Name")
    private String designStaffName;

    @Column(name = "Production_Staff_Name")
    private String productionStaffName;

    @Column(name = "Status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "Product_Id", insertable = false, updatable = false)
    private Product product;
}
