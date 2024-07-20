package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import java.util.Date;

@Entity
@Table(name = "Diamond_Price_List")
public class DiamondPriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "origin")
    private String origin;

    @Column(name = "carat_weight_from")
    private Float carat_weight_from;

    @Column(name = "carat_weight_to")
    private Float carat_weight_to;

    @Column(name = "color")
    private String color;

    @Column(name = "clarity")
    private String clarity;

    @Column(name = "cut")
    private String cut;

    @Min(value = 0, message = "The price must be greater than or equal to 0")
    @Column(name = "price")
    private Float price;

    @PastOrPresent
    @Column(name = "eff_date")
    private Date eff_date;

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

    public Float getCarat_weight_from() {
        return carat_weight_from;
    }

    public void setCarat_weight_from(Float carat_weight_from) {
        this.carat_weight_from = carat_weight_from;
    }

    public Float getCarat_weight_to() {
        return carat_weight_to;
    }

    public void setCarat_weight_to(Float carat_weight_to) {
        this.carat_weight_to = carat_weight_to;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getEff_date() {
        return eff_date;
    }

    public void setEff_date(Date eff_date) {
        this.eff_date = eff_date;
    }
}
