package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.util.Date;

import com.example.demo.validation.PastOrPresentDate;

@Entity
@Table(name = "GemPriceList")
public class GemPriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Origin")
    private String origin;

    @Column(name = "Carat_Weight")
    private float caratWeight;

    @Column(name = "Color")
    private String color;

    @Column(name = "Clarity")
    private String clarity;

    @Column(name = "Cut")
    private String cut;

    @Min(value = 0, message = "The price must be greater than or equal to 0")
    @Column(name = "Price")
    private float price;

    @PastOrPresentDate
    @Column(name = "Eff_Date")
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
        return caratWeight;
    }

    public void setCaratWeight(float caratWeight) {
        this.caratWeight = caratWeight;
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
