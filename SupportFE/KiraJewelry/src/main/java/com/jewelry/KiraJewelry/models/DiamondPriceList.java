package com.jewelry.KiraJewelry.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Diamond_Price_List")
public class DiamondPriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "origin")
    private String origin;

    @Column(name = "carat_weight_from")
    private Float carat_Weight_From;

    @Column(name = "carat_weight_to")
    private Float carat_Weight_To;

    @Column(name = "color")
    private String color;

    @Column(name = "clarity")
    private String clarity;

    @Column(name = "cut")
    private String cut;

    @Min(value = 0, message = "The price must be greater than or equal to 0")
    @Column(name = "price")
    private float price;

    @PastOrPresent
    @Column(name = "eff_date")
    private Date eff_Date;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Float getCarat_Weight_From() {
        return carat_Weight_From;
    }

    public void setCarat_Weight_From(Float carat_Weight_From) {
        this.carat_Weight_From = carat_Weight_From;
    }

    public Float getCarat_Weight_To() {
        return carat_Weight_To;
    }

    public void setCarat_Weight_To(Float carat_Weight_To) {
        this.carat_Weight_To = carat_Weight_To;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public String getCut() {
        return cut;
    }

    public void setCut(String cut) {
        this.cut = cut;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getEff_Date() {
        return eff_Date;
    }

    public void setEff_Date(Date eff_Date) {
        this.eff_Date = eff_Date;
    }

}
