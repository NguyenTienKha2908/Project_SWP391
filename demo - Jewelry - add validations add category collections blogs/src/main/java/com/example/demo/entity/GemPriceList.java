package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.util.Date;

import com.example.demo.validation.PastOrPresentDate;

@Entity
@Table(name = "Gem_Price_List")
public class GemPriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "origin")
    private String origin;

    @Column(name = "carat_weight")
    private float carat_Weight;

    @Column(name = "color")
    private String color;

    @Column(name = "clarity")
    private String clarity;

    @Column(name = "cut")
    private String cut;

    @Min(value = 0, message = "The price must be greater than or equal to 0")
    @Column(name = "price")
    private float price;

    @PastOrPresentDate
    @Column(name = "eff_date")
    private Date effDate;

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

    public float getCaratWeight() {
        return carat_Weight;
    }

    public void setCaratWeight(float caratWeight) {
        this.carat_Weight = caratWeight;
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

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

}
