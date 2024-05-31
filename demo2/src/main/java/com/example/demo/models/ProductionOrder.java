package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class ProductionOrder {
    @Id
    @Column(name = "productionOrderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productionOrderId;
    
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "customerId")
    private String customerId;

    @Column(name ="categoryId")
    private String categoryId;

    @Column(name ="materialName")
    private String materialName;

    @Column(name ="materialColor")
    private String materialColor;

    @Column(name ="materialWeight")
    private double materialWeight;

    @Column(name ="materialId")
    private int materialId;

    @Column(name ="gemName")
    private String gemName;

    @Column(name ="gemColor")
    private String gemColor;

    @Column(name ="gemWeight")
    private double gemWeight;

    @Column(name ="gemId")
    private int gemId;

    @Column(name ="productSize")
    private int productSize;

    @Column(name ="description")
    private String description;

    @Column(name ="diamondAmount")
    private double diamondAmount;

    @Column(name ="materialAmount")
    private double materialAmount;

    @Column(name ="productionAmount")
    private double productionAmount;

    @Column(name ="sideMaterialCost")
    private double sideMaterialCost;

    @Column(name ="sideGemCost")
    private double sideGemCost;

    @Column(name ="totalAmount")
    private double totalAmount;

    @Column(name ="salesStaffName")
    private String salesStaffName;

    @Column(name ="designStaffName")
    private String designStaffName;

    @Column(name ="productionStaffName")
    private String productionStaffName;

    @Column(name ="status")
    private String status;

    @Column(name ="productId")
    private int productId;

}
