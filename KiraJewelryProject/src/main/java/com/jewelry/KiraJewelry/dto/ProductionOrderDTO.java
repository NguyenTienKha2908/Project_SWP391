package com.jewelry.KiraJewelry.dto;

import java.util.Date;

import com.jewelry.KiraJewelry.models.Product;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductionOrderDTO {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "production_Order_Id")
    // private String production_Order_Id;

    // @Column(name = "date")
    // @Temporal(TemporalType.DATE)
    // private Date date;

    // @Column(name = "customer_Id")
    // private String customer_Id;

    // @Column(name = "category_Id")
    // private int category_Id;

    // @Column(name = "product_Size")
    // private int product_Size;

    // @Column(name = "img_Url")
    // private String img_Url;

    // @Column(name = "description")
    // private String description;

    // @Column(name = "Q_diamond_Amount")
    // private double q_Diamond_Amount;

    // @Column(name = "Q_material_Amount")
    // private double q_Material_Amount;

    // @Column(name = "Q_production_Amount")
    // private double q_Production_Amount;

    // @Column(name = "Q_total_Amount")
    // private double q_Total_Amount;

    // @Column(name = "O_diamond_Amount")
    // private double o_Diamond_Amount;

    // @Column(name = "O_material_Amount")
    // private double o_Material_Amount;

    // @Column(name = "O_production_Amount")
    // private double o_Production_Amount;

    // @Column(name = "O_total_Amount")
    // private double o_Total_Amount;

    // @Column(name = "sales_Staff_Id")
    // private String sales_Staff_Id;

    // @Column(name = "design_Staff_Id")
    // private String design_Staff_Id;

    // @Column(name = "production_Staff_Id")
    // private String production_Staff_Id;

    // @Column(name = "status")
    // private String status;

    // @ManyToOne
    // @JoinColumn(name = "product_Id")
    // private Product product;
    private String production_Order_Id;
    private int category_Id;

    private int product_Size;
    private String img_Url;
    private String description;

    private double q_Diamond_Amount;
    private double q_Material_Amount;
    private double q_Production_Amount;
    private double q_Total_Amount;

    private double o_Diamond_Amount;
    private double o_Material_Amount;
    private double o_Production_Amount;
    private double o_Total_Amount;

    // Default constructor
    public ProductionOrderDTO() {
    }

    // Parameterized constructor
    public ProductionOrderDTO(
            String production_Order_Id,
            int category_Id,
            int product_Size,
            String img_url,
            double q_Diamond_Amount, double q_Material_Amount, double q_Production_Amount, double q_Total_Amount,
            double o_Diamond_Amount, double o_Material_Amount, double o_Production_Amount, double o_Total_Amount) {
        this.production_Order_Id = production_Order_Id;
        this.category_Id = category_Id;
        this.product_Size = product_Size;
        this.img_Url = img_url;
        this.q_Diamond_Amount = q_Diamond_Amount;
        this.q_Material_Amount = q_Material_Amount;
        this.q_Production_Amount = q_Production_Amount;
        this.q_Total_Amount = q_Total_Amount;
        this.o_Diamond_Amount = o_Diamond_Amount;
        this.o_Material_Amount = o_Material_Amount;
        this.o_Production_Amount = o_Production_Amount;
        this.o_Total_Amount = o_Total_Amount;
    }

}
